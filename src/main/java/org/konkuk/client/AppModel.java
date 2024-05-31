package org.konkuk.client;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.student.Student;
import org.konkuk.common.verify.VerifierFactory;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.konkuk.client.ui.Strings.*;
import static org.konkuk.common.DefaultPaths.STUDENTS_PATH;

public class AppModel {
    private static AppModel instance = null;
    private final Map<ObserveOf, List<Runnable>> runnableObserverMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<ObserveOf, List<Consumer>> consumerObserverMap = Collections.synchronizedMap(new HashMap<>());
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final VerifierFactory verifierFactory = new VerifierFactory();
    private final List<ProgressTracker> trackers = Collections.synchronizedList(new LinkedList<>());
    private final List<Student> students = Collections.synchronizedList(new LinkedList<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> selectedVerifiedDegree = Collections.synchronizedList(new LinkedList<>());

    private Student committingStudent = null;

    private boolean isStudentListLoaded = false;

    public AppModel() {
        instance = this;
    }

    public static AppModel getInstance() {
        return instance != null ? instance : new AppModel();
    }

    public void observe(ObserveOf observeOf, Runnable callback) {
        if (observeOf == null) {
            return;
        }
        if (!runnableObserverMap.containsKey(observeOf)) {
            runnableObserverMap.put(observeOf, new LinkedList<>());
        }
        runnableObserverMap.get(observeOf).add(callback);
    }

    public void observe(ObserveOf observeOf, Consumer<Object> callback) {
        if (observeOf == null) {
            return;
        }
        if (!consumerObserverMap.containsKey(observeOf)) {
            consumerObserverMap.put(observeOf, new LinkedList<>());
        }
        consumerObserverMap.get(observeOf).add(callback);
    }

    private void notify(ObserveOf observeOf) {
        if (observeOf == null) {
            return;
        }
        List<Runnable> observers = runnableObserverMap.get(observeOf);
        if (observers != null) {
            observers.forEach(SwingUtilities::invokeLater);
        }
        if (observeOf.parent != null) {
            notify(observeOf.parent);
        }
    }

    private void notify(ObserveOf observeOf, Object o) {
        if (observeOf == null) {
            return;
        }
        List<Consumer> observers = consumerObserverMap.get(observeOf);
        if (observers != null) {
            observers.forEach(observer -> SwingUtilities.invokeLater(() -> observer.accept(o)));
        }
        if (observeOf.parent != null) {
            notify(observeOf.parent, o);
        }
    }

    private ProgressTracker newTracker(String message) {
        ProgressTracker tracker = new ProgressTracker(message);
        trackers.add(tracker);
        notify(ObserveOf.ON_TASK_STARTED, tracker);
        tracker.addOnFinishedListener(() -> {
            trackers.remove(tracker);
            notify(ObserveOf.ON_TASK_FINISHED, tracker);
        });
        return tracker;
    }

    public void submitTask(
            Runnable beforeSubmit,
            Runnable afterFinished,
            String message,
            Consumer<ProgressTracker> trackableTask) {
        ProgressTracker tracker = newTracker(message);
        beforeSubmit.run();
        executorService.submit(() -> {
            try {
                trackableTask.accept(tracker);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                tracker.finish();
                afterFinished.run();
            }
        });
    }

    public void loadStudentList() {
        submitTask(
                () -> notify(ObserveOf.ON_STUDENT_LOAD_STARTED),
                () -> notify(ObserveOf.ON_STUDENT_LOADED),
                STUDENTS_LOADING_MESSAGE,
                (tracker) -> {
                    students.clear();
                    File directory = new File(STUDENTS_PATH);
                    File[] studentDirectories = directory.listFiles();
                    if (studentDirectories == null) {
                        tracker.finish();
                        return;
                    }
                    tracker.setMaximum(studentDirectories.length);
                    for (File studentDirectory : studentDirectories) {
                        try {
                            students.add(new Student(studentDirectory.getAbsolutePath()));
                        } catch (InvalidFormatException ignored) {
                        } finally {
                            tracker.increment();
                        }
                    }
                    isStudentListLoaded = true;
                    tracker.finish();
                }
        );
    }

    public void loadVerifier() {
        submitTask(
                () -> notify(ObserveOf.ON_VERIFIER_LOAD_STARTED),
                () -> notify(ObserveOf.ON_VERIFIER_LOADED),
                VERIFIER_LOADING_MESSAGE,
                verifierFactory::loadAllVerifiers
        );
    }

    public void verifyStudent(Student student) throws RuntimeException {
        if (!isStudentListLoaded) {
            throw new RuntimeException("Student list not loaded yet");
        }
        if (!verifierFactory.isLoaded()) {
            throw new RuntimeException("Verifier not loaded yet");
        }

        executorService.submit(() -> {
            if (!student.isLoaded()) {
                student.loadLectures(newTracker(String.format(LECTURES_LOADING_MESSAGES, student)));
            }
            submitTask(
                    () -> notify(ObserveOf.ON_VERIFY_STARTED, student),
                    () -> notify(ObserveOf.ON_VERIFIED, student),
                    String.format(VERIFYING, student),
                    (tracker) -> verifierFactory.newVerifier().verify(student, tracker)
            );
        });
    }

    public void setSelectedStudents(Collection<Student> selectedStudents) {
        this.selectedStudents.clear();
        this.selectedStudents.addAll(selectedStudents);
        notify(ObserveOf.ON_STUDENT_SELECTED, selectedStudents);
    }

    public void setCommittingStudent(Student student) {
        committingStudent = student;
        notify(ObserveOf.ON_COMMIT_STARTED, student);
        if (!committingStudent.isVerified()) {
            verifyStudent(student);
        }
    }

    public void setSelectedVerifiedDegree(Collection<DegreeSnapshot> selectedDegrees) {
        selectedVerifiedDegree.clear();
        selectedVerifiedDegree.addAll(selectedDegrees);
    }

    public void commit() {
        if (committingStudent == null || selectedVerifiedDegree.isEmpty()) {
            return;
        }
        for (DegreeSnapshot degreeSnapshot : selectedVerifiedDegree) {
            if (degreeSnapshot != null) {
                committingStudent.commit(degreeSnapshot);
            }
        }
        notify(ObserveOf.ON_COMMIT_UPDATED, committingStudent);
    }

    public VerifierFactory getVerifierFactory() {
        return verifierFactory;
    }

    public List<ProgressTracker> getTrackers() {
        return trackers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public Student getCommittingStudent() {
        return committingStudent;
    }

    public List<DegreeSnapshot> getSelectedVerifiedDegree() {
        return selectedVerifiedDegree;
    }

    public enum ObserveOf {
        ON_TASK_STARTED,
        ON_TASK_FINISHED,

        ON_VERIFIER_LOAD_STARTED,
        ON_VERIFIER_LOADED,

        ON_VERIFY_STARTED,
        ON_VERIFIED,

        ON_STUDENT_LOAD_STARTED,
        ON_STUDENT_LOADED,

        ON_STUDENT_SELECTED,
        ON_COMMIT_STARTED,
        ON_COMMIT_UPDATED;

        private final ObserveOf parent;

        ObserveOf() {
            this.parent = null;
        }

        ObserveOf(ObserveOf parent) {
            this.parent = parent;
        }

        public boolean isIn(ObserveOf other) {
            if (this == other) {
                return true;
            }
            if (parent != null) {
                return parent.isIn(other);
            }
            return false;
        }
    }
}
