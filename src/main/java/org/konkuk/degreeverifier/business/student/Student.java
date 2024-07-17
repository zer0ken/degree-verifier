package org.konkuk.degreeverifier.business.student;

import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.VerifierBundle;
import org.konkuk.degreeverifier.business.verify.csv.CsvExportable;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class Student extends LinkedHashSet<Lecture> implements CsvExportable, Comparable<Student> {
    public final String id;
    public final String name;
    public final String university;

    private boolean verified = false;

    private List<VerifierBundle> verifiedBundles = null;
    private final VerifierBundle committedDegrees = new VerifierBundle();
    private final VerifierBundle sufficientDegrees = new VerifierBundle();
    private final VerifierBundle insufficientDegrees = new VerifierBundle();
    private final VerifierBundle notVerifiedDegrees = new VerifierBundle();

    private final SnapshotBundle earlyCommittedDegrees = new SnapshotBundle();

    public Student(String name, String id, String university) {
        this.name = name;
        this.id = id;
        this.university = university;
    }

    synchronized public void setVerifiedBundles(List<VerifierBundle> verifiedBundles) {
        this.verifiedBundles = verifiedBundles;
        verifiedBundles.forEach(sufficientDegrees::putAll);
        verified = true;
        committedDegrees.clear();
        updateSufficientInsufficientDegrees();
    }

    synchronized public void setNotVerifiedDegrees(VerifierBundle notVerifiedDegrees) {
        this.notVerifiedDegrees.clear();
        this.notVerifiedDegrees.putAll(notVerifiedDegrees);
    }

    synchronized public void setEarlyCommittedDegrees(Collection<DegreeSnapshot> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            earlyCommittedDegrees.clear();
            return;
        }

        for (DegreeSnapshot degree : degrees) {
            if (degree != null) {
                earlyCommittedDegrees.put(degree.toString(), degree);
            }
        }
        updateSufficientInsufficientDegrees();
    }

    synchronized public void commitAll(Collection<DegreeVerifier> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            return;
        }
        for (DegreeVerifier degree : degrees) {
            if (degree != null && sufficientDegrees.containsKey(degree.toString())) {
                committedDegrees.put(degree.toString(), degree);
                updateSufficientInsufficientDegrees();
            }
        }
    }

    synchronized public void commitRecommendedBundle() {
        if (verifiedBundles.isEmpty()) {
            return;
        }

        sufficientDegrees.clear();
        insufficientDegrees.clear();

        committedDegrees.putAll(getRecommendedBundle());

        updateSufficientInsufficientDegrees();
    }

    synchronized public VerifierBundle getRecommendedBundle() {
        for (VerifierBundle bundle : verifiedBundles) {
            if (bundle.keySet().containsAll(committedDegrees.keySet())) {
                return bundle;
            }
        }
        return new VerifierBundle();
    }

    synchronized public boolean isSufficientBundle(VerifierBundle bundle) {
        if (!bundle.keySet().containsAll(committedDegrees.keySet())) {
            return false;
        }
        for (String key : earlyCommittedDegrees.keySet()) {
            if (!bundle.containsKey(key)) {
                return false;
            }
            if (bundle.get(key).optimizeLike(earlyCommittedDegrees.get(key)) == null) {
                return false;
            }
        }
        return true;
    }

    synchronized public void decommitAll(Collection<DegreeVerifier> degrees) {
        if (degrees == null || degrees.isEmpty()) {
            return;
        }
        for (DegreeVerifier degree : degrees) {
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

        if (committedDegrees.isEmpty() && earlyCommittedDegrees.isEmpty()) {
            for (VerifierBundle bundle : verifiedBundles) {
                sufficientDegrees.putAll(bundle);
            }
            return;
        }

        for (VerifierBundle bundle : verifiedBundles) {
            if (isSufficientBundle(bundle)) {
                sufficientDegrees.putAll(bundle);
            } else {
                insufficientDegrees.putAll(bundle);
            }
        }
        for (String committedKey : committedDegrees.keySet()) {
            this.committedDegrees.put(committedKey, sufficientDegrees.get(committedKey));
            sufficientDegrees.remove(committedKey);
            insufficientDegrees.remove(committedKey);
        }
        for (String sufficientKey : sufficientDegrees.keySet()) {
            insufficientDegrees.remove(sufficientKey);
        }
    }

    public VerifierBundle getCommittedDegrees() {
        return committedDegrees;
    }

    public VerifierBundle getSufficientDegrees() {
        return sufficientDegrees;
    }

    public VerifierBundle getInsufficientDegrees() {
        return insufficientDegrees;
    }

    public VerifierBundle getNotVerifiedDegrees() {
        return notVerifiedDegrees;
    }

    public SnapshotBundle getEarlyCommittedDegrees() {
        return earlyCommittedDegrees;
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
        SnapshotBundle exportBundle = new SnapshotBundle();
        for (String key : committedDegrees.keySet()) {
            DegreeVerifier degree = committedDegrees.get(key);
            if (earlyCommittedDegrees.containsKey(key) && degree.optimizeLike(earlyCommittedDegrees.get(key)) != null) {
                continue;
            }
            if (!exportBundle.containsKey(key) || exportBundle.get(key).criteria.version < degree.version) {
                exportBundle.put(degree.degreeName, degree.optimize());
            }
        }
        for (DegreeSnapshot snapshot : earlyCommittedDegrees.values()) {
            exportBundle.put(snapshot.criteria.degreeName, snapshot);
        }

        StringBuilder sb = new StringBuilder();
        for (DegreeSnapshot degreeSnapshot : exportBundle.values()) {
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
