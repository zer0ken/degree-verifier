package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.csv.Commit;
import org.konkuk.degreeverifier.business.verify.csv.Transcript;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_LOADING_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.TRANSCRIPT_LOADING_MESSAGE;

public class AppModel extends Observable {
    protected static final AppModel instance = new AppModel();

    protected AppModel() {
    }

    public static AppModel getInstance() {
        return instance;
    }

    private Student committingStudent = null;
    private boolean transcriptLoaded = false;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final VerifierFactory verifierFactory = VerifierFactory.getInstance();
    private final NavigableMap<String, Student> students = Collections.synchronizedNavigableMap(new TreeMap<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeSnapshot> selectedVerifiedDegree = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeSnapshot> selectedCommittedDegree = Collections.synchronizedList(new ArrayList<>());

    public void submitTask(
            Runnable beforeSubmit,
            Runnable task,
            Runnable afterFinished
    ) {
        // TODO: 2024-07-14 for debug. return this after test.
//        beforeSubmit.run();
//        executorService.submit(() -> {
//            task.run();
//            afterFinished.run();
//        });

        beforeSubmit.run();
        task.run();
        afterFinished.run();
    }

    public void loadTranscript(File file) {
        submitTask(
                () -> notify(On.TRANSCRIPT_LOAD_STARTED, students),
                () -> {
                    students.clear();
                    List<List<String>> table = FileUtil.fromCsvFile(file);
                    if (!Transcript.isValidHeader(table.get(0).toArray(new String[0]))) {
                        return;
                    }
                    ProgressTracker tracker = new ProgressTracker(TRANSCRIPT_LOADING_MESSAGE);
                    tracker.setMaximum(table.size() - 1);
                    for (List<String> row : table.subList(1, table.size())) {
                        Student student = new Student(row.get(0), row.get(1), row.get(2));
                        if (!students.containsKey(student.toString())) {
                            students.put(student.toString(), student);
                        }
                        student = students.get(student.toString());
                        Lecture lecture = new Lecture(
                                row.get(3), row.get(4), row.get(5), Integer.parseInt(row.get(6)), row.get(7)
                        );
                        student.add(lecture);
                    }
                    transcriptLoaded = true;
                    tracker.finish();
                },
                () -> {
                    notify(On.TRANSCRIPT_LOADED, students);
                    verifyAllStudents();
                }
        );
    }

    public void loadCommit(File file) {
        submitTask(
                () -> notify(On.COMMIT_LOAD_STARTED, students),
                () -> {
                    List<List<String>> table = FileUtil.fromCsvFile(file);
                    if (!Commit.isValidHeader(table.get(0).toArray(new String[0]))) {
                        return;
                    }
                    ProgressTracker tracker = new ProgressTracker(COMMIT_LOADING_MESSAGE);
                    tracker.setMaximum(table.size() - 1);
                    Map<String, SnapshotBundle> bundleMap = new HashMap<>();
                    for (List<String> row : table.subList(1, table.size())) {
                        Student student = students.get(new Student(row.get(0), row.get(1), row.get(2)).toString());
                        if (student == null) {
                            continue;
                        }
                        SnapshotBundle bundle = bundleMap.get(student.toString());
                        if (bundle == null) {
                            bundle = new SnapshotBundle();
                            bundleMap.put(student.toString(), bundle);
                        }

                        String[] lectureNames = new String[10];
                        Integer[] lectureCredits = new Integer[10];
                        for (int i = 6, j = 0; i < row.size(); i += 2, j++) {
                            lectureNames[j] = row.get(i);
                            lectureCredits[j] = Integer.parseInt(row.get(i + 1));
                        }

                        DegreeSnapshot degreeSnapshot = new DegreeSnapshot(
                                row.get(4),
                                Integer.parseInt(row.get(3)),
                                Integer.parseInt(row.get(5)),
                                lectureNames,
                                lectureCredits
                        );
                        bundle.put(degreeSnapshot.toString(), degreeSnapshot);
                    }
                    for (String key : bundleMap.keySet()) {
                        Student student = students.get(key);
                        student.clearCommit();
                        student.commitAll(bundleMap.get(student.toString()).values());
                    }
                    transcriptLoaded = true;
                    tracker.finish();

                },
                () -> {
                    notify(On.COMMIT_LOADED, students);
                    if (committingStudent != null) {
                        notify(On.COMMIT_UPDATED, committingStudent);
                    }
                }
        );
    }

    public void loadLatestVerifiers() {
        submitTask(
                () -> notify(On.VERIFIER_LOAD_STARTED, verifierFactory),
                verifierFactory::loadLatestVerifiers,
                () -> notify(On.VERIFIER_LOADED, verifierFactory)
        );
    }

    public void loadVerifiers(String directory) {
        submitTask(
                () -> notify(On.VERIFIER_LOAD_STARTED, verifierFactory),
                () -> verifierFactory.loadVerifiers(directory),
                () -> notify(On.VERIFIER_LOADED, verifierFactory)
        );
    }

    public void verifyStudent(Student student) {
        submitTask(
                () -> notify(On.VERIFY_STARTED, student),
                () -> verifierFactory.createVerifier().verify(student),
                () -> {
                    notify(On.VERIFIED, student);
                    if (student.equals(committingStudent)) {
                        notify(On.COMMIT_UPDATED, student);
                    }
                }
        );
    }

    public void verifyAllStudents() {
        for (Student student : students.values()) {
            verifyStudent(student);
        }
    }

    public void verifySelectedStudents() {
        for (Student student : selectedStudents) {
            verifyStudent(student);
        }
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
        if (committingStudent == null) {
            startCommit(students.firstEntry().getValue());
        } else {
            startCommit(students.higherEntry(committingStudent.toString()).getValue());
        }
    }

    public void startCommitPrevious() {
        Student previousStudent = students.lowerEntry(committingStudent.toString()).getValue();
        if (previousStudent != null) {
            startCommit(previousStudent);
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

    synchronized public void export(File file) {
        FileUtil.toCsvFile(file, Commit.HEADER, students.values());
    }

    public void commitAllStudentAutomatically() {
        for (Student student : students.values()) {
            executorService.submit(() -> {
                        notify(On.VERIFY_STARTED, student);
                        verifierFactory.createVerifier().verify(student);
                        notify(On.VERIFIED, student);
                        student.commitRecommendedBundle();
                        if (student.equals(committingStudent)) {
                            notify(On.COMMIT_UPDATED, student);
                        }
                    }
            );
        }
    }

    public void updateVerifiers(Collection<DegreeCriteria> criteriaCollection) {
        executorService.submit(() -> {
            verifierFactory.updateAllVerifiers(criteriaCollection);
            notify(On.VERIFIER_LOADED, verifierFactory);
        });
    }

    public int getCommittingStudentIndex() {
        return committingStudent == null ? -1 : new LinkedList<>(students.values()).indexOf(committingStudent);
    }

    public boolean isStudentMapEmpty() {
        return students.isEmpty();
    }

    public boolean hasNextStudentToCommit() {
        return committingStudent == null || students.higherEntry(committingStudent.toString()) != null;
    }

    public boolean hasPreviousStudentToCommit() {
        return committingStudent != null && students.lowerEntry(committingStudent.toString()) != null;
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

    public boolean isTranscriptLoaded() {
        return transcriptLoaded;
    }

    public boolean isVerifierLoaded() {
        return verifierFactory.isLoaded();
    }

    public VerifierFactory getVerifierFactory() {
        return verifierFactory;
    }

    public enum On implements Event {
        VERIFIER_LOAD_STARTED,
        VERIFIER_LOADED,

        VERIFY_STARTED,
        VERIFIED,

        TRANSCRIPT_LOAD_STARTED,
        TRANSCRIPT_LOADED,

        COMMIT_LOAD_STARTED,
        COMMIT_LOADED,

        STUDENT_SELECTED,

        COMMIT_STARTED,

        VERIFIED_DEGREE_SELECTED,
        COMMITTED_DEGREE_SELECTED,

        COMMIT_UPDATED,

        LECTURE_UPDATED,
    }
}
