package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;
import org.konkuk.common.LectureData;
import org.konkuk.common.criteria.LectureCriteria;
import org.konkuk.common.snapshot.LectureSnapshot;
import org.konkuk.common.snapshot.Snapshot;

import java.util.LinkedList;
import java.util.List;


/**
 * 이 클래스는 주어진 상태에서 특정 교과목을 이수했는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:39:55.673Z
 */
public class LectureVerifier extends LectureCriteria implements Verifiable, Creditizable, Snapshotable {
    private Lecture matchedLecture = null;

    private boolean pruned = false;
    private boolean holding = false;

    public LectureVerifier(LectureCriteria toCopy) {
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
        pruned = matchedLecture == null;
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

    // todo: minimumGrade를 고려하도록 수정
    private boolean match(Lecture lecture) {
        return lectureName.equals(lecture.name);
    }

    public void hold() {
        if (matchedLecture == null) {
            holding = true;
            return;
        }
        if (isNonExclusive() || !matchedLecture.isUsed()) {
            holding = true;
        }
        if (isNonExclusive()) {
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
        return new LectureSnapshot(
                new LectureCriteria(this),
                matchedLecture != null ? new LectureData(matchedLecture) : null,
                holding
        );
    }
}
