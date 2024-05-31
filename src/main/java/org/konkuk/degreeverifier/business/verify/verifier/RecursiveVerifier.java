package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.*;


/**
 * 이 클래스는 주어진 상태에서 재귀적으로 정의된 복잡한 검사 기준을 통과할 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:41:30.622Z
 */
public class RecursiveVerifier extends RecursiveCriteria implements Verifiable, Creditizable, Estimable, Snapshotable {
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
    public List<LectureVerifier> match(List<Lecture> lectures) throws ImportantCriteriaFailedException {
        List<LectureVerifier> matchedLectureVerifiers;
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
            throw new ImportantCriteriaFailedException("Important recursiveCriteria pruned: " + (label != null ? label : toString()));
        }
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() throws ImportantCriteriaFailedException {
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
            throw new ImportantCriteriaFailedException("Important recursiveCriteria did not verified: " + (label != null ? label : toString()));
        }

        return verified;
    }

    public boolean isPruned() {
        return pruned;
    }

    @Override
    public int creditize() {
        if (lectureVerifier != null) {
            return lectureVerifier.creditize();
        } else if (maximumPass != null) {
            return subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> recursiveVerifier.verified)
                    .sorted((o1, o2) -> o2.estimateCredit() - o1.estimateCredit())
                    .limit(maximumPass)
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.creditize(), Integer::sum);
        } else {
            return subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> recursiveVerifier.verified)
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.creditize(), Integer::sum);
        }
    }

    @Override
    public int estimateCredit() {
        if (lectureVerifier != null) {
            return lectureVerifier.estimateCredit();
        } else if (maximumPass != null) {
            return subRecursiveVerifiers.stream()
                    .sorted((o1, o2) -> o2.estimateCredit() - o1.estimateCredit())
                    .limit(maximumPass)
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.estimateCredit(), Integer::sum);
        } else {
            return subRecursiveVerifiers.stream()
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.estimateCredit(), Integer::sum);
        }
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
