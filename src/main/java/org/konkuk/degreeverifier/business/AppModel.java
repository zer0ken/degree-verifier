package org.konkuk.degreeverifier.business;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.logic.statusbar.ProgressTracker;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.konkuk.degreeverifier.business.AppModel.ObserveOn.ON_INFORMATION_TARGET_UPDATED;
import static org.konkuk.degreeverifier.business.DefaultPaths.STUDENTS_PATH;
import static org.konkuk.degreeverifier.ui.Strings.STUDENTS_LOADING_MESSAGE;

public class AppModel {
    protected static final AppModel instance = new AppModel();

    protected AppModel() {
    }

    public static AppModel getInstance() {
        return instance;
    }

    private final Map<ObserveOn, List<Runnable>> runnableObserverMap = Collections.synchronizedMap(new HashMap<>());
    private final Map<ObserveOn, List<Consumer<Object>>> consumerObserverMap = Collections.synchronizedMap(new HashMap<>());
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final VerifierFactory verifierFactory = new VerifierFactory();
    private final List<Student> students = Collections.synchronizedList(new LinkedList<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> selectedVerifiedDegree = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> selectedCommittedDegree = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> informationTargets = Collections.synchronizedList(new LinkedList<>());

    private Student committingStudent = null;

    private boolean isStudentListLoaded = false;

    public void observe(ObserveOn observeOn, Runnable callback) {
        if (observeOn == null) {
            return;
        }
        if (!runnableObserverMap.containsKey(observeOn)) {
            runnableObserverMap.put(observeOn, Collections.synchronizedList(new LinkedList<>()));
        }
        runnableObserverMap.get(observeOn).add(callback);
    }

    public void observe(ObserveOn observeOn, Consumer<Object> callback) {
        if (observeOn == null) {
            return;
        }
        if (!consumerObserverMap.containsKey(observeOn)) {
            consumerObserverMap.put(observeOn, Collections.synchronizedList(new LinkedList<>()));
        }
        consumerObserverMap.get(observeOn).add(callback);
    }

    private void notify(ObserveOn observeOn) {
        if (observeOn == null) {
            return;
        }
        List<Runnable> observers = runnableObserverMap.get(observeOn);
        if (observers != null) {
            observers.forEach(SwingUtilities::invokeLater);
        }
    }

    private void notify(ObserveOn observeOn, Object o) {
        if (observeOn == null) {
            return;
        }
        List<Consumer<Object>> observers = consumerObserverMap.get(observeOn);
        if (observers != null) {
            observers.forEach(observer -> SwingUtilities.invokeLater(() -> observer.accept(o)));
        }
    }

    public void submitTask(
            Runnable beforeSubmit,
            Runnable afterFinished,
            Runnable task) {
        beforeSubmit.run();
        executorService.submit(() -> {
            task.run();
            afterFinished.run();
        });
    }

    public void loadStudentList() {
        submitTask(
                () -> notify(ObserveOn.ON_STUDENT_LOAD_STARTED),
                () -> notify(ObserveOn.ON_STUDENT_LOADED),
                () -> {
                    students.clear();
                    File directory = new File(STUDENTS_PATH);
                    File[] studentDirectories = directory.listFiles();
                    if (studentDirectories == null) {
                        return;
                    }
                    ProgressTracker tracker = new ProgressTracker(STUDENTS_LOADING_MESSAGE);

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
                () -> notify(ObserveOn.ON_VERIFIER_LOAD_STARTED),
                () -> notify(ObserveOn.ON_VERIFIER_LOADED),
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

        executorService.submit(
                () -> {
                    if (!student.isLoaded()) {
                        student.loadLectures();
                        if (student.equals(committingStudent)) {
                            notify(ObserveOn.ON_LECTURE_UPDATED, student);
                        }
                    }
                    submitTask(
                            () -> notify(ObserveOn.ON_VERIFY_STARTED, student),
                            () -> {
                                notify(ObserveOn.ON_VERIFIED, student);
                                if (student.equals(committingStudent)) {
                                    notify(ObserveOn.ON_COMMIT_UPDATED, student);
                                }
                            },
                            () -> verifierFactory.newVerifier().verify(student)
                    );
                }
        );
    }

    public void setInformationTargets(Collection<DegreeSnapshot> degreeSnapshots) {
        informationTargets.clear();
        informationTargets.addAll(degreeSnapshots);
        notify(ON_INFORMATION_TARGET_UPDATED, informationTargets);
    }

    public void setSelectedStudents(Collection<Student> selectedStudents) {
        this.selectedStudents.clear();
        this.selectedStudents.addAll(selectedStudents);
        notify(ObserveOn.ON_STUDENT_SELECTED, selectedStudents);
    }

    public void startCommit() {
        if (selectedStudents.isEmpty()) {
            return;
        }
        committingStudent = selectedStudents.get(0);
        notify(ObserveOn.ON_LECTURE_UPDATED, committingStudent);
        notify(ObserveOn.ON_COMMIT_UPDATED, committingStudent);
        if (!committingStudent.isVerified()) {
            verifyStudent(committingStudent);
        }
    }

    public void setSelectedVerifiedDegree(Collection<DegreeSnapshot> selectedDegrees) {
        selectedVerifiedDegree.clear();
        selectedVerifiedDegree.addAll(selectedDegrees);
        notify(ObserveOn.ON_VERIFIED_DEGREE_SELECTED, selectedDegrees);
    }

    public void setSelectedCommittedDegree(Collection<DegreeSnapshot> selectedDegrees) {
        selectedCommittedDegree.clear();
        selectedCommittedDegree.addAll(selectedDegrees);
        notify(ObserveOn.ON_COMMITTED_DEGREE_SELECTED, selectedDegrees);
        notify(ObserveOn.ON_COMMITTED_DEGREE_SELECTED, selectedDegrees);
    }

    public void commitDegrees() {
        if (committingStudent == null || selectedVerifiedDegree.isEmpty()) {
            return;
        }
        committingStudent.commitAll(selectedVerifiedDegree);
        notify(ObserveOn.ON_COMMIT_UPDATED, committingStudent);
    }

    public void commitRecommendedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }

        committingStudent.commitFirstBundle();
        notify(ObserveOn.ON_COMMIT_UPDATED, committingStudent);
    }

    public void decommitDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.decommitAll(selectedCommittedDegree);
        notify(ObserveOn.ON_COMMIT_UPDATED, committingStudent);
    }

    public void clearCommittedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.clearCommit();
        notify(ObserveOn.ON_COMMIT_UPDATED, committingStudent);
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

    public List<DegreeSnapshot> getSelectedCommittedDegree() {
        return selectedCommittedDegree;
    }

    public enum ObserveOn {
        ON_VERIFIER_LOAD_STARTED,
        ON_VERIFIER_LOADED,

        ON_VERIFY_STARTED,
        ON_VERIFIED,

        ON_STUDENT_LOAD_STARTED,
        ON_STUDENT_LOADED,

        ON_STUDENT_SELECTED,

        ON_VERIFIED_DEGREE_SELECTED,
        ON_COMMITTED_DEGREE_SELECTED,

        ON_COMMIT_UPDATED,

        ON_LECTURE_UPDATED,

        ON_INFORMATION_TARGET_UPDATED
    }
}
