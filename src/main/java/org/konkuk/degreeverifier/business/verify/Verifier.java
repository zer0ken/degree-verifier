package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.logic.ProgressTracker;
import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.verifier.LectureVerifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Verifier extends LinkedList<DegreeVerifier> {
    public Verifier(LinkedList<DegreeVerifier> toCopy) {
        for (DegreeVerifier degreeVerifier : toCopy) {
            add(new DegreeVerifier(degreeVerifier));
        }
    }

    public void verify(Student student, ProgressTracker tracker) {
        List<Lecture> lectures = new LinkedList<>(student);

        // 1. Match and Prune
        List<LectureVerifier> exclusiveLectureVerifiers = new ArrayList<>();
        for (DegreeVerifier degreeVerifier : this) {
            exclusiveLectureVerifiers.addAll(degreeVerifier.match(lectures));
        }

        // 2-1. Not Exist Exclusive Verifier
        List<SnapshotBundle> snapshotBundles = new LinkedList<>();

        if (exclusiveLectureVerifiers.isEmpty()) {
            SnapshotBundle snapshotBundle = new SnapshotBundle();
            for (DegreeVerifier degreeVerifier : this) {
                if (degreeVerifier.verify()) {
                    snapshotBundle.put(degreeVerifier.degreeName, (DegreeSnapshot) degreeVerifier.takeSnapshot());
                }
            }
            snapshotBundles.add(snapshotBundle);
        }
        // TODO: 2024-05-31 implement here
        // 2-2. There Exists Exclusive Verifiers
        else {
        }

        // 3. Finish
        student.setVerifiedSnapshotBundles(snapshotBundles);
        tracker.finish();
    }
}
