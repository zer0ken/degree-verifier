package org.konkuk.degreeverifier.business.student;

import org.konkuk.degreeverifier.business.csv.CsvExportable;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.SnapshotBundle;
import org.konkuk.degreeverifier.business.verify.VerifierBundle;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Student extends LinkedHashSet<Lecture> implements CsvExportable, Comparable<Student> {
    public final String campus;
    public final String university;
    public final String department;
    public final String id;
    public final String gender;
    public final String name;
    public final String year;
    private final String birth;
    private final String registeredSemester;

    private boolean verified = false;

    private final List<VerifierBundle> verifiedBundles = new LinkedList<>();
    private final VerifierBundle committedDegrees = new VerifierBundle();
    private final VerifierBundle sufficientDegrees = new VerifierBundle();
    private final VerifierBundle insufficientDegrees = new VerifierBundle();
    private final VerifierBundle notVerifiedDegrees = new VerifierBundle();

    private final SnapshotBundle earlyCommittedDegrees = new SnapshotBundle();

    public Student(String campus, String university, String department,
                   String id, String gender, String name, String year,
                   String birth, String registeredSemester) {
        this.campus = campus;
        this.department = department;
        this.gender = gender;
        this.name = name;
        this.id = id;
        this.university = university;
        this.year = year;
        this.birth = birth;
        this.registeredSemester = registeredSemester;
    }

    public Student(String university, String name, String id) {
        this.university = university;
        this.name = name;
        this.id = id;

        campus = null;
        department = null;
        gender = null;
        year = null;
        birth = null;
        registeredSemester = null;
    }

    synchronized public void setVerifiedBundles(List<VerifierBundle> verifiedBundles) {
        this.verifiedBundles.addAll(verifiedBundles);
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
            if (isSufficientBundle(bundle)) {
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
            if (bundle.containsKey(key) &&
                    bundle.get(key).optimizeLike(earlyCommittedDegrees.get(key)) == null) {
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

    synchronized public String toCsv(AppModel.ExportMode exportMode) {
        // set bundle
        SnapshotBundle exportBundle = getExportBundle(exportMode);

        // build csv string
        StringBuilder sb = new StringBuilder();
        for (DegreeSnapshot degreeSnapshot : exportBundle.values()) {
            sb.append(String.format(
                    degreeSnapshot.toCsv(exportMode == AppModel.ExportMode.NEW_AND_OLD),
                    university,
                    department,
                    name,
                    id,
                    year,
                    birth,
                    registeredSemester
            )).append("\n");
        }
        return sb.toString();
    }

    @Override
    synchronized public String toCsv() {
        return toCsv(AppModel.getInstance().getExportMode());
    }

    public SnapshotBundle getExportBundle(AppModel.ExportMode exportMode) {
        SnapshotBundle exportBundle = new SnapshotBundle();
        SnapshotBundle foundEarlyCommittedDegrees = new SnapshotBundle();
        for (String key : committedDegrees.keySet()) {
            DegreeVerifier degree = committedDegrees.get(key);
            if (earlyCommittedDegrees.containsKey(key)) {
                DegreeSnapshot foundEarlyCommittedSnapshot = degree.optimizeLike(earlyCommittedDegrees.get(key));
                if (foundEarlyCommittedSnapshot != null) {
                    foundEarlyCommittedDegrees.put(foundEarlyCommittedSnapshot.criteria.degreeName, foundEarlyCommittedSnapshot);
                }
                continue;
            }
            if (!exportBundle.containsKey(degree.degreeName) ||
                    exportBundle.get(degree.degreeName).criteria.version < degree.version) {
                exportBundle.put(degree.degreeName, degree.optimize());
            }
        }
        // set bundle
        switch (exportMode) {
            case NEW_AND_OLD:
                for (DegreeSnapshot snapshot : earlyCommittedDegrees.values()) {
                    exportBundle.put(snapshot.criteria.degreeName, snapshot);
                }
                break;
            case NEW_ONLY:
                for (DegreeSnapshot snapshot : earlyCommittedDegrees.values()) {
                    exportBundle.remove(snapshot.criteria.degreeName);
                }
                break;
            case VALIDATE_OLD:
                exportBundle = new SnapshotBundle();
                for (DegreeSnapshot snapshot : earlyCommittedDegrees.values()) {
                    exportBundle.put(snapshot.criteria.degreeName, snapshot);
                }
                for (DegreeSnapshot snapshot : foundEarlyCommittedDegrees.values()) {
                    exportBundle.put(snapshot.criteria.degreeName, snapshot);
                }
                break;
        }
        return exportBundle;
    }

    @Override
    public void clear() {
        super.clear();
        resetCommit();
    }

    public void resetCommit() {
        verifiedBundles.clear();
        notVerifiedDegrees.clear();
        sufficientDegrees.clear();
        insufficientDegrees.clear();
        committedDegrees.clear();
        verified = false;
    }

    @Override
    public int compareTo(Student o) {
        return toString().compareTo(o.toString());
    }
}
