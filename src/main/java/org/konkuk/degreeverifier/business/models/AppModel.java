package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.csv.Commit;
import org.konkuk.degreeverifier.business.csv.Transcript;
import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.commitframe.actions.ExportCommitAction;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class AppModel extends Observable {
    protected static final AppModel instance = new AppModel();

    protected AppModel() {
    }

    public static AppModel getInstance() {
        return instance;
    }

    private Student committingStudent = null;
    private boolean transcriptLoaded = false;
    private boolean commitLoaded = false;

    private final ExecutorService executorService = MyExecutorService.getInstance();
    private final VerifierFactory verifierFactory = VerifierFactory.getInstance();
    private NavigableMap<String, Student> students = Collections.synchronizedNavigableMap(new TreeMap<>());
    private final List<Student> selectedStudents = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeVerifier> selectedVerifiedDegree = Collections.synchronizedList(new ArrayList<>());
    private final List<DegreeVerifier> selectedCommittedDegree = Collections.synchronizedList(new ArrayList<>());

    private List<String> earlyCommitTableHeader = new LinkedList<>();
    private List<List<String>> earlyCommitTable = new LinkedList<>();

    private List<String> transcriptTableHeader = new LinkedList<>();
    private List<List<String>> transcriptTable = new LinkedList<>();

    private File verifierDirectory = null;
    private File transcriptFile = null;
    private File earlyCommitFile = null;
    private File aliasesFile = null;

    private ExportMode exportMode = ExportMode.VALIDATE_OLD;

    public enum ExportMode {
        NEW_AND_OLD,
        NEW_ONLY,
        VALIDATE_OLD,
    }

    public void submitTask(
            Runnable beforeSubmit,
            Runnable task,
            Runnable afterFinished
    ) {
        beforeSubmit.run();
        task.run();
        afterFinished.run();
        executorService.submit(() -> {
        });
    }

    public void loadTranscript(File file) {
        submitTask(
                () -> {
                    transcriptLoaded = false;
                    notify(On.TRANSCRIPT_LOAD_STARTED, students);
                },
                () -> {
                    ProgressTracker tracker = new ProgressTracker(TRANSCRIPT_LOADING_MESSAGE);

                    for (Student student : students.values()) {
                        tracker.finish();
                        student.clear();
                    }

                    List<List<String>> table = FileUtil.fromCsvFile(file);
                    if (Transcript.isValidHeader(table.get(0))) {
                        loadStudentsFromTranscriptTable(table, tracker);
                    } else if (Commit.isValidHeader(table.get(0))) {
                        loadStudentsFromCommitTable(table, tracker);
                    } else {
                        tracker.finish();
                        return;
                    }
                    transcriptFile = file;
                },
                () -> {
                    transcriptLoaded = true;
                    notify(On.TRANSCRIPT_LOADED, students);
                    startCommit(null);
                }
        );
    }

    private void loadStudentsFromTranscriptTable(List<List<String>> table, ProgressTracker tracker) {
        NavigableMap<String, Student> newStudentMap = new TreeMap<>();
        Map<Transcript.ColumnName, Integer> nameToIndex = Transcript.getColumnIndexMap(table.get(0));
        BiFunction<List<String>, Transcript.ColumnName, String> get = (row, columnName) -> {
            int index = nameToIndex.get(columnName);
            return row.size() > index ? row.get(index) : null;
        };
        transcriptTableHeader = table.remove(0);
        transcriptTable = table;

        tracker.setMaximum(table.size());
        for (List<String> row : table) {
            Student student = new Student(
                    get.apply(row, Transcript.ColumnName.CAMPUS),
                    get.apply(row, Transcript.ColumnName.UNIVERSITY),
                    get.apply(row, Transcript.ColumnName.DEPARTMENT),
                    get.apply(row, Transcript.ColumnName.STUDENT_ID),
                    get.apply(row, Transcript.ColumnName.GENDER),
                    get.apply(row, Transcript.ColumnName.STUDENT_NAME),
                    get.apply(row, Transcript.ColumnName.STUDENT_YEAR),
                    get.apply(row, Transcript.ColumnName.BIRTH),
                    get.apply(row, Transcript.ColumnName.REGISTERED_SEMESTER)
            );
            if (!students.containsKey(student.toString())) {
                students.put(student.toString(), student);
            }
            student = students.get(student.toString());
            Lecture lecture = new Lecture(
                    get.apply(row, Transcript.ColumnName.COURSE_YEAR),
                    get.apply(row, Transcript.ColumnName.SEMESTER),
                    get.apply(row, Transcript.ColumnName.REF_NO),
                    get.apply(row, Transcript.ColumnName.CODE),
                    get.apply(row, Transcript.ColumnName.COURSE_NAME),
                    get.apply(row, Transcript.ColumnName.CLASSIFICATION),
                    Integer.parseInt(get.apply(row, Transcript.ColumnName.CREDIT)),
                    get.apply(row, Transcript.ColumnName.GRADE)
            );
            student.add(lecture);
            newStudentMap.put(student.toString(), student);
            tracker.increment();
        }
        students = newStudentMap;

        if (commitLoaded) {
            fetchFromEarlyCommitTable();
        }

        tracker.finish();
    }

    private void loadStudentsFromCommitTable(List<List<String>> table, ProgressTracker tracker) {
        NavigableMap<String, Student> newStudentMap = new TreeMap<>();
        Map<Commit.ColumnName, Integer> nameToIndex = Commit.getColumnIndexMap(table.get(0));
        BiFunction<List<String>, Commit.ColumnName, String> get = (row, columnName) -> {
            int index = nameToIndex.get(columnName);
            return row.size() > index && !row.get(index).isEmpty()
                    ? row.get(index)
                    : "";
        };
        transcriptTableHeader = table.remove(0);
        transcriptTable = table;

        tracker.setMaximum(table.size());
        for (List<String> row : table) {
            Student student = new Student(
                    null,
                    get.apply(row, Commit.ColumnName.UNIVERSITY),
                    get.apply(row, Commit.ColumnName.DEPARTMENT),
                    get.apply(row, Commit.ColumnName.STUDENT_ID),
                    null,
                    get.apply(row, Commit.ColumnName.STUDENT_NAME),
                    get.apply(row, Commit.ColumnName.STUDENT_YEAR),
                    get.apply(row, Commit.ColumnName.BIRTH),
                    get.apply(row, Commit.ColumnName.REGISTERED_SEMESTER)
            );
            if (!newStudentMap.containsKey(student.toString())) {
                newStudentMap.put(student.toString(), student);
            }
            student = newStudentMap.get(student.toString());
            for (int i = 0; i < 5; i++) {
                String courseName = get.apply(row, Commit.ColumnName.valueOf("COURSE_" + (i + 1)));
                if (courseName.isEmpty()) {
                    break;
                }
                String courseCredit = get.apply(row, Commit.ColumnName.valueOf("COURSE_CREDIT_" + (i + 1)));

                Lecture lecture = new Lecture(
                        null,
                        null,
                        null,
                        null,
                        courseName,
                        null,
                        courseCredit.isEmpty()
                                ? null
                                : Integer.parseInt(courseCredit),
                        null
                );
                student.add(lecture);
            }
            newStudentMap.put(student.toString(), student);
            tracker.increment();
        }

        students = newStudentMap;

        if (commitLoaded) {
            fetchFromEarlyCommitTable();
        }

        tracker.finish();
    }

    public void loadAliases(File file) {
        submitTask(
                () -> notify(On.ALIASES_LOAD_STARTED, file),
                () -> {
                    verifierFactory.loadAliases(file);
                    aliasesFile = file;
                },
                () -> {
                    notify(On.ALIASES_LOADED, file);
                    verifyAllStudents();
                }
        );
    }

    public void loadCommit(File file) {
        submitTask(
                () -> notify(On.COMMIT_LOAD_STARTED, students),
                () -> {
                    List<List<String>> table = FileUtil.fromCsvFile(file);
                    if (!Commit.isValidHeader(table.get(0))) {
                        return;
                    }
                    earlyCommitTableHeader = table.remove(0);
                    earlyCommitTable = table;
                    earlyCommitFile = file;

                    fetchFromEarlyCommitTable();

                    commitLoaded = true;
                },
                () -> {
                    notify(On.COMMIT_LOADED, students);
                    startCommit(null);
                }
        );
    }

    public void fetchFromEarlyCommitTable() {
        Map<Commit.ColumnName, Integer> nameToIndex = Commit.getColumnIndexMap(earlyCommitTableHeader);
        BiFunction<List<String>, Commit.ColumnName, String> get = (row, columnName) -> {
            int index = nameToIndex.get(columnName);
            return row.size() > index ? row.get(index) : "";
        };

        ProgressTracker tracker = new ProgressTracker(COMMIT_LOADING_MESSAGE);
        tracker.setMaximum(earlyCommitTable.size());
        Map<String, SnapshotBundle> bundleMap = new HashMap<>();
        for (List<String> row : earlyCommitTable) {
            Student student = students.get(new Student(
                    get.apply(row, Commit.ColumnName.UNIVERSITY),
                    get.apply(row, Commit.ColumnName.STUDENT_NAME),
                    get.apply(row, Commit.ColumnName.STUDENT_ID)
            ).toString());
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
            for (int i = 1; i <= 5; i++) {
                lectureNames[i - 1] = get.apply(row, Commit.ColumnName.valueOf("COURSE_" + i));
                if (lectureNames[i - 1].isEmpty()) {
                    lectureNames[i - 1] = null;
                    lectureCredits[i - 1] = null;
                    break;
                }
                String courseCredit = get.apply(row, Commit.ColumnName.valueOf("COURSE_CREDIT_" + i));
                lectureCredits[i - 1] = courseCredit.isEmpty() ? null : Integer.parseInt(courseCredit);
            }

            String version = get.apply(row, Commit.ColumnName.VERSION);
            String totalCredit = get.apply(row, Commit.ColumnName.TOTAL_CREDIT);
            String requiredCredit = get.apply(row, Commit.ColumnName.REQUIRED_CREDIT);

            DegreeSnapshot degreeSnapshot = new DegreeSnapshot(
                    get.apply(row, Commit.ColumnName.DEGREE_NAME),
                    Integer.parseInt(version),
                    totalCredit.isEmpty() ? null : Integer.parseInt(totalCredit),
                    requiredCredit.isEmpty() ? null : Integer.parseInt(requiredCredit),
                    lectureNames,
                    lectureCredits
            );
            bundle.put(degreeSnapshot.toString(), degreeSnapshot);
        }
        for (String key : bundleMap.keySet()) {
            Student student = students.get(key);
            student.decommitAll();
            student.setEarlyCommittedDegrees(bundleMap.get(student.toString()).values());
        }
        tracker.finish();
    }

    public void loadVerifiers(File directory) {
        submitTask(
                () -> {
                    verifierFactory.clear();
                    notify(On.VERIFIER_LOAD_STARTED, verifierFactory);
                },
                () -> {
                    verifierFactory.loadVerifiers(directory);
                    verifierDirectory = directory;
                },
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
                        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, student);
                    }
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
        InformationModel.getInstance().updateInformationTarget(new ArrayList<>());
        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_STARTED, committingStudent);
        if (committingStudent != null && !committingStudent.isVerified() && verifierFactory.isLoaded()) {
            executorService.submit(() -> verifyStudent(committingStudent));
        }
    }

    public void startCommit() {
        startCommit(selectedStudents.get(0));
    }

    public void startCommitNext() {
        startCommit(getNextStudentToCommit());
    }

    public void startCommitPrevious() {
        startCommit(getPreviousStudentToCommit());
    }

    public void setSelectedVerifiedDegree(Collection<DegreeVerifier> selectedDegrees) {
        selectedVerifiedDegree.clear();
        selectedVerifiedDegree.addAll(selectedDegrees);
        notify(On.VERIFIED_DEGREE_SELECTED, selectedDegrees);
    }

    public void setSelectedCommittedDegree(Collection<DegreeVerifier> selectedDegrees) {
        selectedCommittedDegree.clear();
        selectedCommittedDegree.addAll(selectedDegrees);
        notify(On.COMMITTED_DEGREE_SELECTED, selectedDegrees);
    }

    public void commitDegrees() {
        if (committingStudent == null || selectedVerifiedDegree.isEmpty()) {
            return;
        }
        committingStudent.commitAll(selectedVerifiedDegree);
        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, null);
    }

    public void commitRecommendedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }

        committingStudent.commitRecommendedBundle();
        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, null);
    }

    public void decommitDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.decommitAll(selectedCommittedDegree);
        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, null);
    }

    public void clearCommittedDegrees() {
        if (committingStudent == null || !committingStudent.isVerified()) {
            return;
        }
        committingStudent.decommitAll();
        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
        notify(On.COMMIT_UPDATED, null);
    }

    synchronized public void export(File file) {
        FileUtil.toCsvFile(file, Commit.ColumnName.getNames(), students.values());
    }

    private Runnable getVerifyAllTask() {
        return () -> {
            ProgressTracker tracker = new ProgressTracker(VERIFYING_ALL);
            List<Callable<Object>> verifyTasks = new ArrayList<>(students.size());
            for (Student student : students.values()) {
                verifyTasks.add(() -> {
                    student.resetCommit();
                    verifierFactory.createVerifier().verify(student);
                    tracker.increment();
                    return null;
                });
            }

            if (!verifyTasks.isEmpty()) {
                tracker.setMaximum(verifyTasks.size());
                try {
                    executorService.invokeAll(verifyTasks);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            tracker.finish();
        };
    }

    public void verifyAllStudents() {
        submitTask(
                () -> {
                },
                getVerifyAllTask(),
                () -> {
                    if (committingStudent != null) {
                        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
                    }
                }
        );
    }

    private Runnable getCommitAllAutomaticallyTask() {
        return () -> {
            getVerifyAllTask().run();

            ProgressTracker tracker = new ProgressTracker(AUTO_COMMITTING_ALL);

            List<Callable<Object>> commitTasks = new ArrayList<>(students.size());
            for (Student student : students.values()) {
                commitTasks.add(() -> {
                    student.commitRecommendedBundle();
                    tracker.increment();
                    return null;
                });
            }

            if (!commitTasks.isEmpty()) {
                tracker.setMaximum(commitTasks.size());
                try {
                    executorService.invokeAll(commitTasks);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            tracker.finish();

            if (committingStudent != null) {
                notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
            }
            notify(On.COMMIT_UPDATED, null);
        };
    }

    public void commitAllStudentAutomatically() {
        submitTask(
                () -> {
                },
                getCommitAllAutomaticallyTask(),
                () -> {
                    if (committingStudent != null) {
                        notify(On.SELECTED_STUDENT_COMMIT_UPDATED, committingStudent);
                    }
                }
        );
    }

    public void commitAllStudentAutomaticallyAndExport(ActionEvent event) {
        executorService.submit(() -> {
            getCommitAllAutomaticallyTask().run();
            new ExportCommitAction().actionPerformed(event);
        });
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

    public Student getNextStudentToCommit() {
        if (!isTranscriptLoaded()) {
            return null;
        }
        Student current = committingStudent;
        if (current == null) {
            current = students.firstEntry().getValue();
        } else {
            current = students.higherEntry(current.toString()) != null
                    ? students.higherEntry(current.toString()).getValue()
                    : null;
        }
        while (current != null && current.getCommittedDegrees().isEmpty() && current.getEarlyCommittedDegrees().isEmpty()) {
            current = students.higherEntry(current.toString()) != null
                    ? students.higherEntry(current.toString()).getValue()
                    : null;
        }
        return current;
    }

    public Student getPreviousStudentToCommit() {
        if (!isTranscriptLoaded()) {
            return null;
        }
        Student current = committingStudent;
        if (current == null) {
            current = students.lastEntry().getValue();
        } else {
            current = students.lowerEntry(current.toString()) != null
                    ? students.lowerEntry(current.toString()).getValue()
                    : null;
        }
        while (current != null && current.getCommittedDegrees().isEmpty() && current.getEarlyCommittedDegrees().isEmpty()) {
            current = students.lowerEntry(current.toString()) != null
                    ? students.lowerEntry(current.toString()).getValue()
                    : null;
        }
        return current;
    }

    public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public Student getCommittingStudent() {
        return committingStudent;
    }

    public List<DegreeVerifier> getSelectedVerifiedDegree() {
        return selectedVerifiedDegree;
    }

    public List<DegreeVerifier> getSelectedCommittedDegree() {
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

    public NavigableMap<String, Student> getStudents() {
        return students;
    }

    public List<List<String>> getEarlyCommitTable() {
        return earlyCommitTable;
    }

    public List<List<String>> getCommitTableNewAndOld() {
        List<List<String>> commitTable = new LinkedList<>();

        for (Student student : students.values()) {
            String csv = student.toCsv(ExportMode.NEW_AND_OLD).trim();
            if (csv.isEmpty()) {
                continue;
            }
            String[] rows = csv.split("\n");
            for (String row : rows) {
                commitTable.add(Arrays.asList(row.split(",")));
            }
        }

        return commitTable;
    }

    public List<List<String>> getCommitTableNewOnly() {
        List<List<String>> commitTable = new LinkedList<>();

        for (Student student : students.values()) {
            String csv = student.toCsv(ExportMode.NEW_ONLY).trim();
            if (csv.isEmpty()) {
                continue;
            }
            String[] rows = csv.split("\n");
            for (String row : rows) {
                commitTable.add(Arrays.asList(row.split(",")));
            }
        }

        return commitTable;
    }

    public List<List<String>> getCommitTableValidateOld() {
        List<List<String>> commitTable = new LinkedList<>();

        for (Student student : students.values()) {
            String csv = student.toCsv(ExportMode.VALIDATE_OLD).trim();
            if (csv.isEmpty()) {
                continue;
            }
            String[] rows = csv.split("\n");
            for (String row : rows) {
                commitTable.add(Arrays.asList(row.split(",")));
            }
        }

        return commitTable;
    }

    public List<List<String>> getTranscriptTable() {
        return transcriptTable;
    }

    public boolean isCommitLoaded() {
        return commitLoaded;
    }

    public List<String> getTranscriptTableHeader() {
        return transcriptTableHeader;
    }

    public List<String> getEarlyCommitTableHeader() {
        return earlyCommitTableHeader;
    }

    public File getVerifierDirectory() {
        return verifierDirectory;
    }

    public File getAliasesFile() {
        return aliasesFile;
    }

    public File getEarlyCommitFile() {
        return earlyCommitFile;
    }

    public File getTranscriptFile() {
        return transcriptFile;
    }

    public void setExportMode(ExportMode exportMode) {
        this.exportMode = exportMode;
    }

    public ExportMode getExportMode() {
        return exportMode;
    }

    public enum On implements Event {
        VERIFIER_LOAD_STARTED,
        VERIFIER_LOADED,

        VERIFY_STARTED,
        VERIFIED,

        ALIASES_LOAD_STARTED,
        ALIASES_LOADED,

        TRANSCRIPT_LOAD_STARTED,
        TRANSCRIPT_LOADED,

        COMMIT_LOAD_STARTED,
        COMMIT_LOADED,

        STUDENT_SELECTED,

        COMMIT_STARTED,

        VERIFIED_DEGREE_SELECTED,
        COMMITTED_DEGREE_SELECTED,

        SELECTED_STUDENT_COMMIT_UPDATED,
        COMMIT_UPDATED,
    }
}
