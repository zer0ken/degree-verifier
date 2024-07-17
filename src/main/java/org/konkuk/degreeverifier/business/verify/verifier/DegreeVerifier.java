package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 이 클래스는 주어진 상태에서 특정 학위를 인정받을 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:51:21.363Z
 */
public class DegreeVerifier extends DegreeCriteria implements Creditizable, Snapshotable {
    private final RecursiveVerifier recursiveVerifier;
    public final Set<String> sufficientDegrees = new LinkedHashSet<>();
    public final Set<String> insufficientDegrees = new LinkedHashSet<>();

    private boolean verified = false;

    public DegreeVerifier(DegreeCriteria toCopy) {
        super(toCopy);
        recursiveVerifier = new RecursiveVerifier(recursiveCriteria);
        recursiveVerifier.setDegreeName(toCopy.degreeName);
    }

    public List<LectureVerifier> match(List<Lecture> lectures) {
        return recursiveVerifier.match(lectures, getMinimumSemester(), getMaximumSemester());
    }

    public boolean verify() {
        try {
            verified = recursiveVerifier.verify() && creditize() >= minimumCredit;
        } catch (ImportantCriteriaFailedException e) {
            verified = false;
        }
        return verified;
    }

    public DegreeSnapshot optimize() {
        if (!verified) {
            return (DegreeSnapshot) takeSnapshot();
        }
        List<LectureVerifier> lectureVerifiers = new ArrayList<>();
        List<LectureVerifier> holdingLectureVerifiers = new ArrayList<>();

        LinkedList<RecursiveVerifier> queue = new LinkedList<>();
        queue.add(recursiveVerifier);
        while (!queue.isEmpty()) {
            RecursiveVerifier cur = queue.pop();
            if (cur.getLectureVerifier() != null && cur.getLectureVerifier().getMatchedLecture() != null) {
                lectureVerifiers.add(cur.getLectureVerifier());
            } else {
                queue.addAll(cur.getSubRecursiveVerifiers());
            }
        }
        lectureVerifiers.sort(Comparator.comparingInt(LectureVerifier::creditize));

        int totalCredit = lectureVerifiers.stream().reduce(0, (acc, lecture) -> acc + lecture.creditize(), Integer::sum);
        while (totalCredit >= minimumCredit + lectureVerifiers.get(0).creditize()) {
            LectureVerifier discarded = null;
            for (LectureVerifier lectureVerifier : lectureVerifiers) {
                if (lectureVerifier.isHolding()) {
                    holdingLectureVerifiers.add(lectureVerifier);
                } else {
                    continue;
                }
                lectureVerifier.release();
                if (!verify()) {
                    lectureVerifier.hold();
                } else {
                    discarded = lectureVerifier;
                    break;
                }
            }
            if (discarded == null) {
                break;
            } else {
                lectureVerifiers.remove(discarded);
                holdingLectureVerifiers.add(discarded);
                totalCredit = lectureVerifiers.stream().reduce(0, (acc, lecture) -> acc + lecture.creditize(), Integer::sum);
            }
        }
        verify();
        DegreeSnapshot snapshot = (DegreeSnapshot) takeSnapshot();
        for (LectureVerifier holdingLectureVerifier : holdingLectureVerifiers) {
            holdingLectureVerifier.hold();
        }
        return snapshot;
    }

    public DegreeSnapshot optimizeLike(DegreeSnapshot snapshot) {
        if (!Objects.equals(degreeName, snapshot.criteria.degreeName) ||
                !Objects.equals(version, snapshot.criteria.version)) {
            return null;
        }

        List<LectureVerifier> lectureVerifiers = new ArrayList<>();
        Set<String> lecturesToPreserve = snapshot.lectureSnapshots.stream()
                .map(l -> l.matched.name).collect(Collectors.toSet());

        LinkedList<RecursiveVerifier> queue = new LinkedList<>();
        queue.add(recursiveVerifier);
        while (!queue.isEmpty()) {
            RecursiveVerifier cur = queue.pop();
            if (cur.getLectureVerifier() != null && cur.getLectureVerifier().getMatchedLecture() != null) {
                lectureVerifiers.add(cur.getLectureVerifier());
            } else {
                queue.addAll(cur.getSubRecursiveVerifiers());
            }
        }

        List<LectureVerifier> holdingLectureVerifiers = new ArrayList<>();
        for (LectureVerifier lectureVerifier : lectureVerifiers) {
            if (!lectureVerifier.isHolding()) {
                continue;
            }
            holdingLectureVerifiers.add(lectureVerifier);
            lectureVerifier.release();
            if (lecturesToPreserve.contains(lectureVerifier.getMatchedLecture().name)) {
                lectureVerifier.hold();
            }
        }

        verify();
        DegreeSnapshot newSnapshot = (DegreeSnapshot) takeSnapshot();
        for (LectureVerifier holdingLectureVerifier : holdingLectureVerifiers) {
            holdingLectureVerifier.hold();
        }

        if (snapshot.verified) {
            return newSnapshot;
        } else {
            return null;
        }
    }

    public RecursiveVerifier getRecursiveVerifier() {
        return recursiveVerifier;
    }

    @Override
    public int creditize() {
        return recursiveVerifier.creditize();
    }

    @Override
    public Snapshot takeSnapshot() {
        return new DegreeSnapshot(
                new DegreeCriteria(this),
                verified,
                creditize(),
                (RecursiveSnapshot) recursiveVerifier.takeSnapshot()
        );
    }
}
