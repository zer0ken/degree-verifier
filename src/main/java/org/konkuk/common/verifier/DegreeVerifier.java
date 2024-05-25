package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;
import org.konkuk.common.criteria.DegreeCriteria;
import org.konkuk.common.snapshot.Snapshot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 이 클래스는 주어진 상태에서 특정 학위를 인정받을 수 있는지 검증합니다.
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
        List<LectureVerifier> matchedLectureVerifiers = new LinkedList<>();
        try {
            matchedLectureVerifiers = recursiveVerifier.match(lectures);
            pruned = recursiveVerifier.isPruned();
        } catch (RuntimeException e) {
            pruned = true;
        }
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() {
        boolean verified;
        try {
            verified = recursiveVerifier.verify() && creditize() >= minimumCredit;
        } catch (RuntimeException e) {
            verified = false;
        }
        return verified;
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

    public boolean isPruned() {
        return pruned;
    }
}
