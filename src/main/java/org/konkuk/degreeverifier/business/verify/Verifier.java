package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.verifier.LectureVerifier;

import java.util.*;

public class Verifier extends LinkedList<DegreeVerifier> {
    public Verifier(LinkedList<DegreeVerifier> toCopy) {
        for (DegreeVerifier degreeVerifier : toCopy) {
            add(new DegreeVerifier(degreeVerifier));
        }
    }

    public void verify(Student student) {
        student.resetCommit();
        List<Lecture> lectures = new LinkedList<>(student);

        // 1. Match and Hold Lectures
        List<LectureVerifier> exclusiveLectureVerifiers = new ArrayList<>();
        for (DegreeVerifier degreeVerifier : this) {
            exclusiveLectureVerifiers.addAll(degreeVerifier.match(lectures));
        }

        List<VerifierBundle> verifierBundles = new LinkedList<>();
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
            VerifierBundle bundle = new VerifierBundle();
            for (DegreeVerifier degreeVerifier : this) {
                if (degreeVerifier.verify()) {
                    bundle.put(degreeVerifier.toString(), degreeVerifier);
                }
            }
            verifierBundles.add(bundle);
        } else {
            // 2-2. There Exists Exclusive Verifiers
            List<List<LectureVerifier>> groups = new LinkedList<>(groupedExclusiveVerifiers.values());

            verifierBundles.addAll(pickAndVerify(groups));
            verifierBundles.sort((a, b) -> b.size() - a.size());
        }

        // 3. Check Sufficiency
        Map<String, List<DegreeVerifier>> flattened = new LinkedHashMap<>();
        for (VerifierBundle bundle : verifierBundles) {
            for (DegreeVerifier degreeInBundle : bundle.values()) {
                if (!flattened.containsKey(degreeInBundle.toString())) {
                    flattened.put(degreeInBundle.toString(), new LinkedList<>());
                }
                flattened.get(degreeInBundle.toString()).add(degreeInBundle);
            }
        }
        for (VerifierBundle bundle : verifierBundles) {
            for (DegreeVerifier degreeInBundle : bundle.values()) {
                for (DegreeVerifier degreeVerifier : flattened.get(degreeInBundle.toString())) {
                    degreeVerifier.sufficientDegrees.addAll(bundle.keySet());
                }
            }
        }
        for (List<DegreeVerifier> groupedVerifiers : flattened.values()) {
            DegreeVerifier degree = groupedVerifiers.get(0);
            Set<String> insufficientDegrees = new LinkedHashSet<>(flattened.keySet());
            insufficientDegrees.removeAll(degree.sufficientDegrees);
            for (DegreeVerifier degreeVerifier : groupedVerifiers) {
                degreeVerifier.insufficientDegrees.addAll(insufficientDegrees);
            }
        }

        // 4. Set
        VerifierBundle notVerifiedDegrees = new VerifierBundle();
        for (List<LectureVerifier> value : groupedExclusiveVerifiers.values()) {
            for (LectureVerifier lectureVerifier : value) {
                lectureVerifier.hold();
            }
        }
        for (DegreeVerifier degreeVerifier : this) {
            if (verifierBundles.stream().noneMatch(snapshotBundle -> snapshotBundle.containsKey(degreeVerifier.toString()))) {
                notVerifiedDegrees.put(degreeVerifier.toString(), degreeVerifier);
            }
        }
        verifierBundles.sort((o1, o2) -> o2.size() - o1.size());
        student.setVerifiedBundles(verifierBundles);
        student.setNotVerifiedDegrees(notVerifiedDegrees);
    }

    private List<VerifierBundle> pickAndVerify(List<List<LectureVerifier>> left) {
        List<VerifierBundle> bundles = new LinkedList<>();
        for (LectureVerifier lectureVerifier : left.get(0)) {
            lectureVerifier.hold();
            if (left.size() == 1) {
                VerifierBundle bundle = new VerifierBundle();
                for (DegreeVerifier degreeVerifier : this) {
                    if (degreeVerifier.verify()) {
                        bundle.put(degreeVerifier.toString(), new DegreeVerifier(degreeVerifier));
                    }
                }
                if (!bundle.isEmpty()) {
                    bundles.add(bundle);
                }
            } else {
                bundles.addAll(pickAndVerify(left.subList(1, left.size())));
            }
            lectureVerifier.release();
        }
        return bundles;
    }
}
