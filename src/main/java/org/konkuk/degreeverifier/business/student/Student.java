package org.konkuk.degreeverifier.business.student;

import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.csv.CsvExportable;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class Student extends LinkedHashSet<Lecture> implements CsvExportable, Comparable<Student> {
    public final String id;
    public final String name;
    public final String university;

    private boolean verified = false;

    private List<SnapshotBundle> verifiedSnapshotBundles = null;
    private final SnapshotBundle committedDegrees = new SnapshotBundle();
    private final SnapshotBundle sufficientDegrees = new SnapshotBundle();
    private final SnapshotBundle insufficientDegrees = new SnapshotBundle();
    private final SnapshotBundle notVerifiedDegrees = new SnapshotBundle();

    private final File lastExported = null;

    public Student(String name, String id, String university) {
        this.name = name;
        this.id = id;
        this.university = university;
    }

    synchronized public void setVerifiedSnapshotBundles(List<SnapshotBundle> verifiedSnapshotBundles) {
        this.verifiedSnapshotBundles = verifiedSnapshotBundles;
        verifiedSnapshotBundles.forEach(sufficientDegrees::putAll);
        verified = true;
        committedDegrees.clear();
        updateSufficientInsufficientDegrees();
    }

    synchronized public void setNotVerifiedDegrees(SnapshotBundle notVerifiedDegrees) {
        this.notVerifiedDegrees.clear();
        this.notVerifiedDegrees.putAll(notVerifiedDegrees);
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
            updateSufficientInsufficientDegrees();
        }
    }

    synchronized public void commitRecommendedBundle() {
        if (verifiedSnapshotBundles.isEmpty()) {
            return;
        }

        sufficientDegrees.clear();
        insufficientDegrees.clear();

        committedDegrees.putAll(getRecommendedBundle());

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
        updateSufficientInsufficientDegrees();
    }

    synchronized public void clearCommit() {
        committedDegrees.clear();
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

    public File getLastExported() {
        return lastExported;
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

    public SnapshotBundle getNotVerifiedDegrees() {
        return notVerifiedDegrees;
    }

    public boolean isVerified() {
        return verified;
    }

    @Override
    public String toString() {
        return name + "(" + university + " / " + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) {
            return false;
        }
        Student other = (Student) o;

        return id.equals(other.id) && name.equals(other.name);
    }

    @Override
    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        for (DegreeSnapshot degreeSnapshot : committedDegrees.values()) {
            sb.append(name).append(",")
                    .append(id).append(",")
                    .append(university).append(",")
                    .append(degreeSnapshot.toCsv()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Student o) {
        return toString().compareTo(o.toString());
    }
}
