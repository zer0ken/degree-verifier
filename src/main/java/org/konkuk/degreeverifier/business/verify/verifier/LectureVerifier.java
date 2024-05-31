package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.LinkedList;
import java.util.List;


/**
 * 이 클래스는 주어진 상태에서 특정 교과목을 이수했는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:39:55.673Z
 */
public class LectureVerifier extends LectureCriteria implements Verifiable, Creditizable, Estimable, Snapshotable {
    private Lecture matchedLecture = null;  // TODO: 2024-05-31 한 교과목 검사 기준이 여러 교과목에 대해 매치할 수 있도록 수정할 필요가 있을까?

    private boolean pruned = false;
    private boolean holding = false;

    public LectureVerifier(LectureCriteria toCopy) {
        super(toCopy);
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
        List<LectureVerifier> matchedLectureVerifiers = new LinkedList<>();
        for (Lecture lecture : lectures) {
            if (match(lecture)) {
                matchedLecture = lecture;
                if (isNonExclusive()) {
                    hold();
                } else {
                    matchedLectureVerifiers.add(this);
                }
                break;
            }
        }
        pruned = matchedLecture == null;
        return matchedLectureVerifiers;
    }

    @Override
    public boolean verify() {
        return holding;
    }

    // todo: minimumGrade를 고려하도록 수정
    private boolean match(Lecture lecture) {
        return lectureName.equals(lecture.name);
    }

    public void hold() {
        if (matchedLecture == null) {
            holding = false;
            return;
        }
        if (isNonExclusive()) {
            holding = true;
        } else if (!matchedLecture.isUsed()) {
            holding = true;
            matchedLecture.use();
        }
    }

    public void release() {
        holding = false;
        matchedLecture.disuse();
    }

    public boolean isPruned() {
        return pruned;
    }

    @Override
    public int creditize() {
        return pruned ? 0 :
                holding ? matchedLecture.credit : 0;
    }

    @Override
    public int estimateCredit() {
        return pruned ? 0 : matchedLecture.credit;
    }

    @Override
    public Snapshot takeSnapshot() {
        return new LectureSnapshot(
                new LectureCriteria(this),
                matchedLecture != null ? new LectureData(matchedLecture) : null,
                holding
        );
    }

    public String getLectureName() {
        return lectureName;
    }
}
