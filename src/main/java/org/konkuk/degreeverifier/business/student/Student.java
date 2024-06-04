package org.konkuk.degreeverifier.business.student;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.logic.statusbar.ProgressTracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.konkuk.degreeverifier.ui.Strings.EXPORTING_COMMIT_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.LECTURES_LOADING_MESSAGES;

public class Student extends LinkedHashSet<Lecture> {
    private final String directoryName;
    public final String id;
    public final String name;

    private boolean loaded = false;
    private boolean verified = false;

    private List<SnapshotBundle> verifiedSnapshotBundles = null;
    private final SnapshotBundle committedDegrees = new SnapshotBundle();
    private final SnapshotBundle sufficientDegrees = new SnapshotBundle();
    private final SnapshotBundle insufficientDegrees = new SnapshotBundle();

    private File lastExported = null;

    public Student(String directoryName) throws InvalidFormatException {
        File directory = new File(directoryName);
        StringTokenizer tokenizer = new StringTokenizer(directory.getName(), "-");
        if (tokenizer.countTokens() != 2) {
            throw new InvalidFormatException("Wrong directory name format: " + directoryName);
        }
        this.directoryName = directoryName;
        this.id = tokenizer.nextToken().trim();
        this.name = tokenizer.nextToken().trim();
    }

    synchronized public void loadLectures() {
        if (loaded) {
            return;
        }
        ProgressTracker tracker = new ProgressTracker(String.format(LECTURES_LOADING_MESSAGES, this));
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File exportDirectory = new File(getExportDirectoryPath());
        if (!exportDirectory.exists()) {
            exportDirectory.mkdir();
        } else if(exportDirectory.listFiles().length != 0) {
            lastExported = Arrays.stream(exportDirectory.listFiles())
                    .sorted((f1, f2) -> f2.getName().substring(8).compareTo(f1.getName().substring(8)))
                    .collect(Collectors.toList()).get(0);
        }

        File[] transcripts = directory.listFiles();
        if (transcripts == null) {
            tracker.finish();
            return;
        }
        tracker.setMaximum(transcripts.length);
        for (File transcript : transcripts) {
            if (transcript.isFile() && transcript.getName().endsWith(".tsv")) {
                List<String[]> tokenizedLines = FileUtil.fromTsvFile(transcript.getAbsolutePath());
                for (String[] tokens : tokenizedLines) {
                    Lecture lecture = new Lecture(
                            tokens[0], tokens[1], tokens[2], tokens[3], tokens[4],
                            Integer.parseInt(tokens[5]),
                            tokens[6], tokens[7]
                    );
                    if (!contains(lecture)) {
                        add(lecture);
                    }
                }
            }
            tracker.increment();
        }
        loaded = true;
        tracker.finish();
    }

    synchronized public void setVerifiedSnapshotBundles(List<SnapshotBundle> verifiedSnapshotBundles) {
        this.verifiedSnapshotBundles = verifiedSnapshotBundles;
        verifiedSnapshotBundles.forEach(sufficientDegrees::putAll);
        verified = true;
        committedDegrees.clear();
        updateSufficientInsufficientDegrees();
    }

    synchronized public void commitAll(Collection<DegreeSnapshot> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            return;
        }
        for (DegreeSnapshot degree : degrees) {
            if (degree == null || !sufficientDegrees.containsKey(degree.toString())) {
                continue;
            }
            committedDegrees.put(degree.toString(), degree);
            exportCommit(false);
            updateSufficientInsufficientDegrees();
        }
    }

    synchronized public void commitRecommendedBundle() {
        if (verifiedSnapshotBundles.isEmpty()) {
            return;
        }

        sufficientDegrees.clear();
        insufficientDegrees.clear();

        committedDegrees.clear();
        committedDegrees.putAll(getRecommendedBundle());

        exportCommit(false);
        updateSufficientInsufficientDegrees();
    }

    synchronized public SnapshotBundle getRecommendedBundle() {
        for (SnapshotBundle bundle : verifiedSnapshotBundles) {
            if (bundle.keySet().containsAll(committedDegrees.keySet())) {
                return bundle;
            }
        }
        return new SnapshotBundle();
    }

    synchronized public void decommitAll(Collection<DegreeSnapshot> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            return;
        }
        for (DegreeSnapshot degree : degrees) {
            if (degree == null || !committedDegrees.containsKey(degree.toString())) {
                return;
            }
            committedDegrees.remove(degree.toString());
        }
        exportCommit(false);
        updateSufficientInsufficientDegrees();
    }

    synchronized public void clearCommit() {
        committedDegrees.clear();
        exportCommit(false);
        updateSufficientInsufficientDegrees();
    }

    synchronized private void updateSufficientInsufficientDegrees() {
        sufficientDegrees.clear();
        insufficientDegrees.clear();

        if (committedDegrees.isEmpty()) {
            for (SnapshotBundle bundle : verifiedSnapshotBundles) {
                sufficientDegrees.putAll(bundle);
            }
            return;
        }

        for (SnapshotBundle bundle : verifiedSnapshotBundles) {
            boolean isSufficientBundle = true;
            for (String key : committedDegrees.keySet()) {
                if (!bundle.containsKey(key)) {
                    isSufficientBundle = false;
                    break;
                }
            }
            if (isSufficientBundle) {
                sufficientDegrees.putAll(bundle);
            } else {
                insufficientDegrees.putAll(bundle);
            }
        }
        for (String committedKey : committedDegrees.keySet()) {
            committedDegrees.put(committedKey, sufficientDegrees.get(committedKey));
            sufficientDegrees.remove(committedKey);
            insufficientDegrees.remove(committedKey);
        }
        for (String sufficientKey : sufficientDegrees.keySet()) {
            insufficientDegrees.remove(sufficientKey);
        }
    }

    public void exportCommit(boolean manually) {
        ProgressTracker tracker = new ProgressTracker(String.format(EXPORTING_COMMIT_MESSAGE, this));
        lastExported = manually
                ? new File(getManualExportFilePath())
                : lastExported.getName().startsWith("자동 저장")
                ? lastExported
                : new File(getAutoExportFilePath());
        FileUtil.exportCommit(lastExported.getAbsolutePath(), getCommittedDegrees().keySet().stream().reduce("", (acc, str) -> acc + str + "\n"));
        tracker.finish();
    }

    public void loadFrom(File file) {
        committedDegrees.clear();
        committedDegrees.putAll(FileUtil.loadCommit(file));
        updateSufficientInsufficientDegrees();
    }

    public File getLastExported() {
        return lastExported;
    }

    public String getManualExportFilePath() {
        return getExportDirectoryPath() + "\\" + new SimpleDateFormat("수동 출력 - yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()) + ".txt";
    }

    public String getAutoExportFilePath() {
        return getExportDirectoryPath() + "\\" + new SimpleDateFormat("자동 저장 - yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()) + ".txt";
    }

    public String getExportDirectoryPath() {
        return DefaultPaths.EXPORT_PATH + "\\" + id + " - " + name;
    }

    public SnapshotBundle getCommittedDegrees() {
        return committedDegrees;
    }

    public SnapshotBundle getSufficientDegrees() {
        return sufficientDegrees;
    }

    public SnapshotBundle getInsufficientDegrees() {
        return insufficientDegrees;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) {
            return false;
        }
        Student other = (Student) o;

        return id.equals(other.id) && name.equals(other.name);
    }
}
