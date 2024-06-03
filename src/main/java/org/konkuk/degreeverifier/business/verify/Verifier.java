package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.verifier.LectureVerifier;
import org.konkuk.degreeverifier.logic.statusbar.ProgressTracker;

import java.util.*;

import static org.konkuk.degreeverifier.ui.Strings.VERIFYING;

public class Verifier extends LinkedList<DegreeVerifier> {
    public Verifier(LinkedList<DegreeVerifier> toCopy) {
        for (DegreeVerifier degreeVerifier : toCopy) {
            add(new DegreeVerifier(degreeVerifier));
        }
    }

    public void verify(Student student) {
        List<Lecture> lectures = new LinkedList<>(student);
        ProgressTracker tracker = new ProgressTracker(String.format(VERIFYING, student));

        // 1. Match and Prune
        List<LectureVerifier> exclusiveLectureVerifiers = new ArrayList<>();
        for (DegreeVerifier degreeVerifier : this) {
            exclusiveLectureVerifiers.addAll(degreeVerifier.match(lectures));
        }

        List<SnapshotBundle> snapshotBundles = new LinkedList<>();
        Map<String, List<LectureVerifier>> groupedExclusiveVerifiers = new LinkedHashMap<>();
        for (LectureVerifier lectureVerifier : exclusiveLectureVerifiers) {
            if (!groupedExclusiveVerifiers.containsKey(lectureVerifier.getMatchedLecture().toString())) {
                groupedExclusiveVerifiers.put(lectureVerifier.getMatchedLecture().toString(), new LinkedList<>());
            }
            groupedExclusiveVerifiers.get(lectureVerifier.getMatchedLecture().toString()).add(lectureVerifier);
        }
        List<String> notDuplicated = new LinkedList<>();
        groupedExclusiveVerifiers.forEach((k, group) -> {
            if (group.size() == 1) {
                group.get(0).hold();
                notDuplicated.add(k);
            } else {
                for (LectureVerifier lectureVerifier : group) {
                    group.forEach(_lectureVerifier -> lectureVerifier.addDuplicatedDegree(_lectureVerifier.getDegreeName()));
                }
            }
        });
        for (String key : notDuplicated) {
            groupedExclusiveVerifiers.remove(key);
        }

        if (groupedExclusiveVerifiers.isEmpty()) {
            // 2-1. Not Exist Exclusive Verifier
            SnapshotBundle snapshotBundle = new SnapshotBundle();
            for (DegreeVerifier degreeVerifier : this) {
                if (degreeVerifier.verify()) {
                    snapshotBundle.put(degreeVerifier.degreeName, (DegreeSnapshot) degreeVerifier.takeSnapshot());
                }
            }
            snapshotBundles.add(snapshotBundle);
        } else {
            // 2-2. There Exists Exclusive Verifiers
            int size = 1;
            for (List<LectureVerifier> list : groupedExclusiveVerifiers.values()) {
                size *= list.size();
            }
            tracker.setMaximum(size);

            List<List<LectureVerifier>> groups = new LinkedList<>(groupedExclusiveVerifiers.values());

            snapshotBundles.addAll(pickAndVerify(groups, tracker));
            snapshotBundles.sort((a, b) -> b.size() - a.size());
        }

        // 3. Finish
        student.setVerifiedSnapshotBundles(snapshotBundles);
        tracker.finish();
    }

    private List<SnapshotBundle> pickAndVerify(List<List<LectureVerifier>> left, ProgressTracker tracker) {
        List<SnapshotBundle> bundles = new LinkedList<>();
        for (LectureVerifier lectureVerifier : left.get(0)) {
            lectureVerifier.hold();
            if (left.size() == 1) {
                tracker.increment();
                SnapshotBundle bundle = new SnapshotBundle();
                for (DegreeVerifier degreeVerifier : this) {
                    if (degreeVerifier.verify()) {
                        bundle.put(degreeVerifier.degreeName, (DegreeSnapshot) degreeVerifier.takeSnapshot());
                    }
                }
                if (!bundle.isEmpty()) {
                    bundles.add(bundle);
                }
            } else {
                bundles.addAll(pickAndVerify(left.subList(1, left.size()), tracker));
            }
            lectureVerifier.release();
        }
        return bundles;
    }
}
