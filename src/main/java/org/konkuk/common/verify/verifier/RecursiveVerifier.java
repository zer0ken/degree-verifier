package org.konkuk.common.verify.verifier;

import org.konkuk.common.student.Lecture;
import org.konkuk.common.verify.criteria.RecursiveCriteria;
import org.konkuk.common.verify.snapshot.LectureSnapshot;
import org.konkuk.common.verify.snapshot.RecursiveSnapshot;
import org.konkuk.common.verify.snapshot.Snapshot;

import java.util.*;


/**
 * 이 클래스는 주어진 상태에서 재귀적으로 정의된 복잡한 검사 기준을 통과할 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:41:30.622Z
 */
public class RecursiveVerifier extends RecursiveCriteria implements Verifiable, Creditizable, Snapshotable {
    private final LectureVerifier lectureVerifier;
    private final List<RecursiveVerifier> subRecursiveVerifiers;

    private boolean pruned = false;
    private boolean verified = false;

    public RecursiveVerifier(RecursiveCriteria toCopy) {
        super(toCopy);
        lectureVerifier = lectureCriteria == null ? null : new LectureVerifier(lectureCriteria);
        subRecursiveVerifiers = new LinkedList<>();
        if (subcriteria != null) {
            Arrays.stream(subcriteria).forEach(criteria -> subRecursiveVerifiers.add(new RecursiveVerifier(criteria)));
        }
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) throws RuntimeException {
        List<LectureVerifier> matchedLectureVerifiers;
        clear();
        if (lectureVerifier != null) {
            matchedLectureVerifiers = lectureVerifier.match(lectures);
            pruned = lectureVerifier.isPruned();
        } else {
            matchedLectureVerifiers = new LinkedList<>();
            subRecursiveVerifiers.forEach(
                    recursiveVerifier -> matchedLectureVerifiers.addAll(recursiveVerifier.match(lectures)));
            int prunedCount = (int) subRecursiveVerifiers.stream().filter(RecursiveVerifier::isPruned).count();
            pruned = needsAllPass() ? prunedCount > 0 : subRecursiveVerifiers.size() - prunedCount < getMinimumPass();
        }
        if (pruned && isImportant()) {
            throw new RuntimeException("Important recursiveCriteria pruned: " + (label != null ? label : toString()));
        }
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() throws RuntimeException {
        if (lectureVerifier != null) {
            verified = lectureVerifier.verify();
        } else {
            int passedCount = (int) subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> !recursiveVerifier.isPruned())
                    .filter(RecursiveVerifier::verify)
                    .count();
            verified = needsAllPass() ? passedCount == subRecursiveVerifiers.size() : passedCount >= getMinimumPass();
        }

        if (isImportant() && !verified) {
            throw new RuntimeException("Important recursiveCriteria did not verified: " + (label != null ? label : toString()));
        }

        return verified;
    }

    @Override
    public void clear() {
        pruned = false;
        verified = false;

        if (lectureVerifier != null) {
            lectureVerifier.clear();
        } else {
            subRecursiveVerifiers.forEach(RecursiveVerifier::clear);
        }
    }

    public boolean isPruned() {
        return pruned;
    }

    @Override
    public int creditize() {
        int credit;

        if (lectureVerifier != null) {
            credit = lectureVerifier.creditize();
        } else {
            credit = subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> recursiveVerifier.verified)
                    .limit(maximumPass)
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.creditize(), Integer::sum);
        }

        return credit;
    }

    @Override
    public Snapshot takeSnapshot() {
        if (lectureVerifier != null) {
            return new RecursiveSnapshot(
                    new RecursiveCriteria(this),
                    verified,
                    (LectureSnapshot) lectureVerifier.takeSnapshot()
            );
        }

        RecursiveSnapshot[] subSnapshot = new RecursiveSnapshot[subRecursiveVerifiers.size()];
        for (int i = 0; i < subRecursiveVerifiers.size(); i++) {
            subSnapshot[i] = (RecursiveSnapshot) subRecursiveVerifiers.get(i).takeSnapshot();
        }
        return new RecursiveSnapshot(new RecursiveCriteria(this), verified, subSnapshot);
    }

    public LectureVerifier getLectureVerifier() {
        return lectureVerifier;
    }

    public List<RecursiveVerifier> getSubRecursiveVerifiers() {
        return subRecursiveVerifiers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isImportant()) {
            sb.append("필수 ");
        }
        if (lectureCriteria != null) {
            sb.append("교과목: ").append(lectureVerifier.lectureName);
        } else if (needsAllPass()) {
            sb.append("검사 그룹: 모두 통과");
        } else if (getMinimumPass() == 0) {
            sb.append("검사 그룹");
        } else {
            sb.append("검사 그룹: ").append(getMinimumPass()).append("개 이상 통과");
        }
        return sb.toString();
    }
}
