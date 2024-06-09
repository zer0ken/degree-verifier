package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.verifier.LectureVerifier;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

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
        Map<String, List<DegreeSnapshot>> flattened = new LinkedHashMap<>();
        for (SnapshotBundle bundle : snapshotBundles) {
            for (DegreeSnapshot degreeInBundle : bundle.values()) {
                if (!flattened.containsKey(degreeInBundle.toString())) {
                    flattened.put(degreeInBundle.toString(), new LinkedList<>());
                }
                flattened.get(degreeInBundle.toString()).add(degreeInBundle);
            }
        }
        for (SnapshotBundle bundle : snapshotBundles) {
            for (DegreeSnapshot degreeInBundle : bundle.values()) {
                for (DegreeSnapshot sufficientDegree : flattened.get(degreeInBundle.toString())) {
                    sufficientDegree.sufficientDegrees.addAll(bundle.keySet());
                }
            }
        }
        for (List<DegreeSnapshot> groupedSnapshots : flattened.values()) {
            DegreeSnapshot degree = groupedSnapshots.get(0);
            Set<String> insufficientDegrees = new LinkedHashSet<>(flattened.keySet());
            insufficientDegrees.removeAll(degree.sufficientDegrees);
            for (DegreeSnapshot degreeSnapshot : groupedSnapshots) {
                degreeSnapshot.insufficientDegrees.addAll(insufficientDegrees);
            }
        }

        student.setVerifiedSnapshotBundles(snapshotBundles);

        SnapshotBundle notVerifiedDegrees = new SnapshotBundle();
        for (List<LectureVerifier> value : groupedExclusiveVerifiers.values()) {
            for (LectureVerifier lectureVerifier : value) {
                lectureVerifier.hold();
            }
        }
        for (DegreeVerifier degreeVerifier : this) {
            if (snapshotBundles.stream().noneMatch(snapshotBundle -> snapshotBundle.containsKey(degreeVerifier.degreeName))) {
                notVerifiedDegrees.put(degreeVerifier.degreeName, (DegreeSnapshot) degreeVerifier.takeSnapshot());
            }
        }
        student.setNotVerifiedDegrees(notVerifiedDegrees);

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
