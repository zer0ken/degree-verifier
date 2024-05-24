package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;
import org.konkuk.common.criteria.RecursiveCriteria;
import org.konkuk.common.snapshot.Snapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecursiveVerifier extends RecursiveCriteria implements Verifiable, Creditizable, Snapshotable {
    private final LectureVerifier lectureVerifier;
    private final List<RecursiveVerifier> subRecursiveVerifiers;

    private boolean pruned = false;

    protected RecursiveVerifier(RecursiveCriteria toCopy) {
        super(toCopy);
        subRecursiveVerifiers = new ArrayList<>();

        if (lectureCriteria != null) {
            lectureVerifier = new LectureVerifier(lectureCriteria);
        } else {
            lectureVerifier = null;
            for (RecursiveCriteria subcriterion : subcriteria) {
                subRecursiveVerifiers.add(new RecursiveVerifier(subcriterion));
            }
        }
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
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
            pruned = needsAllPass() ? prunedCount > 0 : subRecursiveVerifiers.size() - prunedCount < minimumPass;
        }
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() throws RuntimeException {
        boolean verified;

        if (lectureVerifier != null) {
            verified = lectureVerifier.verify();
        } else {
            int passedCount = (int) subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> !recursiveVerifier.isPruned())
                    .filter(RecursiveVerifier::verify)
                    .count();
            verified = needsAllPass() ? passedCount == subRecursiveVerifiers.size() : passedCount >= minimumPass;
        }

        if (important && !verified) {
            throw new RuntimeException("Important recursiveCriteria did not verified: " + (label != null ? label : toString()));
        }

        return verified;
    }

    @Override
    public void clear() {
        pruned = false;
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
            credit = (int) subRecursiveVerifiers.stream()
                    .filter(recursiveVerifier -> !recursiveVerifier.isPruned())
                    .reduce(0, (acc, recursiveVerifier) -> acc + recursiveVerifier.creditize(), Integer::sum);
        }

        return credit;
    }

    @Override
    public Snapshot takeSnapshot() {
        return null;
    }
}
