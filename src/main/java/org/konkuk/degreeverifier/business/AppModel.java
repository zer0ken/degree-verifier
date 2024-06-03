package org.konkuk.degreeverifier.business;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.logic.statusbar.ProgressTracker;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.konkuk.degreeverifier.business.DefaultPaths.STUDENTS_PATH;
import static org.konkuk.degreeverifier.ui.Strings.STUDENTS_LOADING_MESSAGE;

public class AppModel extends Observable {
    protected static final AppModel instance = new AppModel();

    protected AppModel() {
    }

    public static AppModel getInstance() {
        return instance;
    }

    private Student committingStudent = null;
    private boolean isStudentListLoaded = false;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final VerifierFactory verifierFactory = new VerifierFactory();
    private final List<Student> students = Collections.synchronizedList(new LinkedList<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> selectedVerifiedDegree = Collections.synchronizedList(new LinkedList<>());
    private final List<DegreeSnapshot> selectedCommittedDegree = Collections.synchronizedList(new LinkedList<>());

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
                () -> notify(On.STUDENT_LOAD_STARTED, students),
                () -> notify(On.STUDENT_LOADED, students),
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
                () -> notify(On.VERIFIER_LOAD_STARTED, verifierFactory),
                () -> notify(On.VERIFIER_LOADED, verifierFactory),
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
                            notify(On.LECTURE_UPDATED, student);
                        }
                    }
                    submitTask(
                            () -> notify(On.VERIFY_STARTED, student),
                            () -> {
                                notify(On.VERIFIED, student);
                                if (student.equals(committingStudent)) {
                                    notify(On.COMMIT_UPDATED, student);
                                }
                            },
                            () -> verifierFactory.newVerifier().verify(student)
                    );
                }
        );
    }

    public void setSelectedStudents(Collection<Student> selectedStudents) {
        this.selectedStudents.clear();
        this.selectedStudents.addAll(selectedStudents);
        notify(On.STUDENT_SELECTED, selectedStudents);
    }

    public void startCommit() {
        if (selectedStudents.isEmpty()) {
            return;
        }
        committingStudent = selectedStudents.get(0);
        notify(On.LECTURE_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, committingStudent);
        if (!committingStudent.isVerified()) {
            verifyStudent(committingStudent);
        }
    }

    public void setSelectedVerifiedDegree(Collection<DegreeSnapshot> selectedDegrees) {
        selectedVerifiedDegree.clear();
        selectedVerifiedDegree.addAll(selectedDegrees);
        notify(On.VERIFIED_DEGREE_SELECTED, selectedDegrees);
    }

    public void setSelectedCommittedDegree(Collection<DegreeSnapshot> selectedDegrees) {
        selectedCommittedDegree.clear();
        selectedCommittedDegree.addAll(selectedDegrees);
        notify(On.COMMITTED_DEGREE_SELECTED, selectedDegrees);
    }

    public void commitDegrees() {
        if (committingStudent == null || selectedVerifiedDegree.isEmpty()) {
            return;
        }
        committingStudent.commitAll(selectedVerifiedDegree);
        notify(On.COMMIT_UPDATED, committingStudent);
    }

    public void commitRecommendedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }

        committingStudent.commitFirstBundle();
        notify(On.COMMIT_UPDATED, committingStudent);
    }

    public void decommitDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.decommitAll(selectedCommittedDegree);
        notify(On.COMMIT_UPDATED, committingStudent);
    }

    public void clearCommittedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.clearCommit();
        notify(On.COMMIT_UPDATED, committingStudent);
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

    public enum On implements Event {
        VERIFIER_LOAD_STARTED,
        VERIFIER_LOADED,

        VERIFY_STARTED,
        VERIFIED,

        STUDENT_LOAD_STARTED,
        STUDENT_LOADED,

        STUDENT_SELECTED,

        VERIFIED_DEGREE_SELECTED,
        COMMITTED_DEGREE_SELECTED,

        COMMIT_UPDATED,

        LECTURE_UPDATED
    }
}
