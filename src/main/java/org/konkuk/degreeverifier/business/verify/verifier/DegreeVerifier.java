package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * 이 클래스는 주어진 상태에서 특정 학위를 인정받을 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:51:21.363Z
 */
public class DegreeVerifier extends DegreeCriteria implements Creditizable, Snapshotable {
    private final RecursiveVerifier recursiveVerifier;

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

    public void optimize() {
        if (!verified) {
            return;
        }

        List<LectureVerifier> lectureVerifiers = new ArrayList<>();

        LinkedList<RecursiveVerifier> queue = new LinkedList<>();
        queue.add(recursiveVerifier);
        while (!queue.isEmpty()) {
            RecursiveVerifier cur = queue.pop();
            if (cur.getLectureVerifier() != null && cur.getLectureVerifier().getMatchedLecture() != null) {
                LectureVerifier lectureVerifier = cur.getLectureVerifier();
                lectureVerifiers.add(lectureVerifier);
            } else {
                queue.addAll(cur.getSubRecursiveVerifiers());
            }
        }
        lectureVerifiers.sort(Comparator.comparingInt(LectureVerifier::creditize));

        int totalCredit = lectureVerifiers.stream().reduce(0, (acc, lecture) -> acc + lecture.creditize(), Integer::sum);
        while (totalCredit >= minimumCredit + lectureVerifiers.get(0).creditize()) {
            LectureVerifier discarded = null;
            for (LectureVerifier lectureVerifier : lectureVerifiers) {
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
                totalCredit = lectureVerifiers.stream().reduce(0, (acc, lecture) -> acc + lecture.creditize(), Integer::sum);
            }
        }
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
                (RecursiveSnapshot) recursiveVerifier.takeSnapshot()
        );
    }

    public RecursiveVerifier getRecursiveVerifier() {
        return recursiveVerifier;
    }
}
