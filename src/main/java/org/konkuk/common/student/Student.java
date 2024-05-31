package org.konkuk.common.student;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.FileUtil;
import org.konkuk.common.verify.SnapshotBundle;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import java.io.File;
import java.util.*;

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

    public void loadLectures(ProgressTracker tracker) {
        if (loaded) {
            tracker.finish();
            return;
        }
        File directory = new File(directoryName);
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

    public void setVerifiedSnapshotBundles(List<SnapshotBundle> verifiedSnapshotBundles) {
        this.verifiedSnapshotBundles = verifiedSnapshotBundles;
        sufficientDegrees.clear();
        verifiedSnapshotBundles.forEach(sufficientDegrees::putAll);
        insufficientDegrees.clear();
        verified = true;
    }

    public List<SnapshotBundle> getVerifiedSnapshotBundles() {
        return verifiedSnapshotBundles;
    }

    public void commit(DegreeSnapshot degree) {
        if (!sufficientDegrees.containsKey(degree.toString())) {
            return;
        }

        committedDegrees.put(degree.toString(), degree);

        sufficientDegrees.clear();
        insufficientDegrees.clear();

        for (SnapshotBundle bundle : verifiedSnapshotBundles) {
            if (bundle.containsKey(degree.toString())) {
                sufficientDegrees.putAll(bundle);
            } else {
                insufficientDegrees.putAll(bundle);
            }
        }
        for (String committedDegree : committedDegrees.keySet()) {
            sufficientDegrees.remove(committedDegree);
        }
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
