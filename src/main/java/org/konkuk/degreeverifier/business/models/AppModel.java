package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private final VerifierFactory verifierFactory = VerifierFactory.getInstance();
    private final List<Student> students = Collections.synchronizedList(new ArrayList<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeSnapshot> selectedVerifiedDegree = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeSnapshot> selectedCommittedDegree = Collections.synchronizedList(new ArrayList<>());
    private DegreeVerifier selectedVerifier = null;

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
                        } catch (RuntimeException ignored) {
                        } finally {
                            tracker.increment();
                        }
                    }
                    isStudentListLoaded = true;
                    tracker.finish();
                }
        );
    }

    public void loadLatestVerifiers() {
        submitTask(
                () -> notify(On.VERIFIER_LOAD_STARTED, verifierFactory),
                () -> notify(On.VERIFIER_LOADED, verifierFactory),
                verifierFactory::loadLatestVerifiers
        );
    }

    public void loadVerifiers(String directory) {
        submitTask(
                () -> notify(On.VERIFIER_LOAD_STARTED, verifierFactory),
                () -> notify(On.VERIFIER_LOADED, verifierFactory),
                () -> verifierFactory.loadVerifiers(directory)
        );
    }

    public void verifyStudent(Student student) {
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
                            () -> {
                                verifierFactory.createVerifier().verify(student);
                                if (committingStudent.hasCommitFile()) {
                                    committingStudent.loadFrom(committingStudent.getLastExported());
                                }
                            }
                    );
                }
        );
    }

    public void setSelectedStudents(Collection<Student> selectedStudents) {
        this.selectedStudents.clear();
        this.selectedStudents.addAll(selectedStudents);
        notify(On.STUDENT_SELECTED, selectedStudents);
    }

    public void startCommit(Student student) {
        committingStudent = student;
        notify(On.LECTURE_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_STARTED, committingStudent);
        if (!committingStudent.isVerified()) {
            verifyStudent(committingStudent);
        }
    }

    public void startCommit() {
        startCommit(selectedStudents.get(0));
    }

    public void startCommitNext() {
        startCommit(students.get(students.indexOf(committingStudent) + 1));
    }

    public void startCommitPrevious() {
        startCommit(students.get(students.indexOf(committingStudent) - 1));
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

        committingStudent.commitRecommendedBundle();
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

    public void addStudent(String directoryName) {
        Student student;
        student = new Student(directoryName);
        student.loadLectures();
        students.add(student);
        notify(On.STUDENT_LOADED, students);
    }

    public void exportManually() {
        if (committingStudent != null && !committingStudent.isVerified()) {
            return;
        }
        executorService.submit(() -> committingStudent.exportCommit(true));
    }

    public void commitAllStudentAutomatically(boolean excludeAlreadyCommittedStudent) {
        for (Student student : students) {
            if (student.hasCommitFile() && excludeAlreadyCommittedStudent) {
                continue;
            }

            executorService.submit(() -> {
                        if (!student.isLoaded()) {
                            student.loadLectures();
                            if (student.equals(committingStudent)) {
                                notify(On.LECTURE_UPDATED, student);
                            }
                        }
                        notify(On.VERIFY_STARTED, student);
                        verifierFactory.createVerifier().verify(student);
                        notify(On.VERIFIED, student);
                        if (student.hasCommitFile()) {
                            student.loadFrom(student.getLastExported());
                        }
                        student.commitRecommendedBundle();
                        if (student.equals(committingStudent)) {
                            notify(On.COMMIT_UPDATED, student);
                        }
                    }
            );
        }
    }

    public void updateVerifiers(Collection<DegreeCriteria> criteriaCollection) {
        executorService.submit(() ->{
            verifierFactory.updateAllVerifiers(criteriaCollection);
            notify(On.VERIFIER_LOADED, verifierFactory);
        });
    }

    public int getCommittingStudentIndex() {
        return committingStudent == null? -1 : students.indexOf(committingStudent);
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

    public boolean isStudentListLoaded() {
        return isStudentListLoaded;
    }

    public boolean isVerifierLoaded() {
        return verifierFactory.isLoaded();
    }

    public VerifierFactory getVerifierFactory() {
        return verifierFactory;
    }

    public DegreeVerifier getSelectedVerifier() {
        return selectedVerifier;
    }

    public enum On implements Event {
        VERIFIER_LOAD_STARTED,
        VERIFIER_LOADED,

        VERIFY_STARTED,
        VERIFIED,

        STUDENT_LOAD_STARTED,
        STUDENT_LOADED,

        STUDENT_SELECTED,

        COMMIT_STARTED,

        VERIFIED_DEGREE_SELECTED,
        COMMITTED_DEGREE_SELECTED,

        COMMIT_UPDATED,

        LECTURE_UPDATED,
    }
}
