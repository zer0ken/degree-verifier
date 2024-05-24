package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;
import org.konkuk.common.criteria.LectureCriteria;
import org.konkuk.common.snapshot.LectureSnapshot;
import org.konkuk.common.snapshot.Snapshot;

import java.util.LinkedList;
import java.util.List;

public class LectureVerifier extends LectureCriteria implements Verifiable, Creditizable, Snapshotable {
    protected Lecture matchedLecture = null;

    protected boolean pruned = false;
    protected boolean holding = false;

    protected LectureVerifier(LectureCriteria toCopy) {
        super(toCopy);
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
        List<LectureVerifier> matchedLectureVerifiers = new LinkedList<>();
        clear();
        for (Lecture lecture : lectures) {
            if (match(lecture)) {
                matchedLecture = lecture;
                matchedLectureVerifiers.add(this);
            }
        }
        pruned = true;
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() {
        return holding;
    }

    @Override
    public void clear() {
        matchedLecture = null;
        pruned = false;
        holding = false;
    }

    private boolean match(Lecture lecture) {
        return lectureName.equals(lecture.name);
    }

    public void hold() {
        if (matchedLecture == null) {
            holding = true;
            return;
        }
        if (nonExclusive || !matchedLecture.isUsed()) {
            holding = true;
        }
        if (!nonExclusive) {
            matchedLecture.use();
        }
    }

    public void release() {
        holding = false;
    }

    public boolean isPruned() {
        return pruned;
    }

    @Override
    public int creditize() {
        return holding ? matchedLecture.credit : 0;
    }

    @Override
    public Snapshot takeSnapshot() {

        return null;
    }
}
