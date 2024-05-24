package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;
import org.konkuk.common.criteria.DegreeCriteria;
import org.konkuk.common.snapshot.Snapshot;

import java.util.List;

/**
 * 이 클래스는 하나의 학위를 인정받을 수 있는 경우를 확인하는 클래스입니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:51:21.363Z
 */
public class DegreeVerifier extends DegreeCriteria implements Verifiable, Creditizable, Snapshotable {
    private final RecursiveVerifier recursiveVerifier;

    private boolean pruned = false;

    public DegreeVerifier(DegreeCriteria toCopy) {
        super(toCopy);
        recursiveVerifier = new RecursiveVerifier(recursiveCriteria);
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
        clear();
        List<LectureVerifier> matchedLectureVerifiers = recursiveVerifier.match(lectures);
        pruned = recursiveVerifier.isPruned();
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() {
        return recursiveVerifier.verify() && creditize() >= minimumCredit;
    }

    @Override
    public void clear() {
        pruned = false;
    }

    @Override
    public int creditize() {
        return recursiveVerifier.creditize();
    }

    @Override
    public Snapshot takeSnapshot() {
        return null;
    }
}
