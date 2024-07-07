package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * 이 클래스는 주어진 상태에서 재귀적으로 정의된 복잡한 검사 기준을 통과할 수 있는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:41:30.622Z
 */
public class RecursiveVerifier extends RecursiveCriteria implements Creditizable, Estimable, Snapshotable {
    private final LectureVerifier lectureVerifier;
    private final List<RecursiveVerifier> subRecursiveVerifiers;

    private boolean verified = false;

    public RecursiveVerifier(RecursiveCriteria toCopy) {
        super(toCopy);
        lectureVerifier = lectureCriteria == null ? null : new LectureVerifier(lectureCriteria);
        subRecursiveVerifiers = new LinkedList<>();
        if (subcriteria != null) {
            Arrays.stream(subcriteria).forEach(criteria -> subRecursiveVerifiers.add(new RecursiveVerifier(criteria)));
        }
    }

    public List<LectureVerifier> match(List<Lecture> lectures, Semester minimumSemester, Semester maximumSemester) throws ImportantCriteriaFailedException {
        List<LectureVerifier> exclusiveLectureVerifiers;
        if (lectureVerifier != null) {
            exclusiveLectureVerifiers = lectureVerifier.match(lectures, minimumSemester, maximumSemester);
        } else {
            exclusiveLectureVerifiers = new LinkedList<>();
            for (RecursiveVerifier recursiveVerifier : subRecursiveVerifiers) {
                exclusiveLectureVerifiers.addAll(recursiveVerifier.match(lectures, minimumSemester, maximumSemester));
            }
        }
        return exclusiveLectureVerifiers;
    }

    public boolean verify() throws ImportantCriteriaFailedException {
        if (lectureVerifier != null) {
            verified = lectureVerifier.verify();
        } else {
            int passedCount = (int) subRecursiveVerifiers.stream()
                    .filter(RecursiveVerifier::verify)
                    .count();
            verified = needsAllPass() ? passedCount == subRecursiveVerifiers.size() : passedCount >= getMinimumPass();
            if (verified && passedCount == 0) {
                verified = false;
            }
        }

        if (isImportant() && !verified) {
            throw new ImportantCriteriaFailedException("Important recursiveCriteria did not verified: " + (description != null ? description : toString()));
        }

        return verified;
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

    public void setDegreeName(String degreeName) {
        if (lectureVerifier!= null) {
            lectureVerifier.setDegreeName(degreeName);
        }else{
            for (RecursiveVerifier subRecursiveVerifier : subRecursiveVerifiers) {
                subRecursiveVerifier.setDegreeName(degreeName);
            }
        }
    }
}
