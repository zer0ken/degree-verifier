package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.List;

/**
 * 이 클래스는 주어진 상태에서 특정 학위를 인정받을 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:51:21.363Z
 */
public class DegreeVerifier extends DegreeCriteria implements Verifiable, Creditizable, Snapshotable {
    private final RecursiveVerifier recursiveVerifier;

    private boolean verified = false;

    public DegreeVerifier(DegreeCriteria toCopy) {
        super(toCopy);
        recursiveVerifier = new RecursiveVerifier(recursiveCriteria);
        recursiveVerifier.setDegreeName(toCopy.degreeName);
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
        return recursiveVerifier.match(lectures);
    }

    @Override
    public boolean verify() {
        try {
            verified = recursiveVerifier.verify() && creditize() >= minimumCredit;
        } catch (ImportantCriteriaFailedException e) {
            verified = false;
        }
        return verified;
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
