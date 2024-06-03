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

    private boolean holding = false;

    public LectureVerifier(LectureCriteria toCopy) {
        super(toCopy);
    }

    @Override
    public List<LectureVerifier> match(List<Lecture> lectures) {
        List<LectureVerifier> exclusiveLectureVerifiers = new LinkedList<>();
        for (Lecture lecture : lectures) {
            if (match(lecture)) {
                matchedLecture = lecture;
                if (isNonExclusive()) {
                    hold();
                } else {
                    exclusiveLectureVerifiers.add(this);
                }
                break;
            }
        }
        return exclusiveLectureVerifiers;
    }

    @Override
    public boolean verify() {
        return holding;
    }

    private boolean match(Lecture lecture) {
        if (!lecture.name.equals(lectureName)) {
            return false;
        }
        int year = Integer.parseInt(lecture.year);
        int semester = Integer.parseInt(String.valueOf(lecture.semester.charAt(0)));
        // TODO: 2024-06-03 로직 수정: 더 유연한 입력 대응
        if (minimumYear != null) {
            if (year < minimumYear) {
                return false;
            }
            if (minimumSemester != null && year == minimumYear && semester < minimumSemester) {
                return false;
            }
        }
        if (maximumYear != null) {
            if (year > maximumYear) {
                return false;
            }
            if (maximumSemester != null && year == maximumYear && semester > maximumSemester) {
                return false;
            }
        }
        return true;
    }

    public void hold() {
        holding = true;
    }

    public void release() {
        holding = false;
    }

    @Override
    public int creditize() {
        return holding ? matchedLecture.credit : 0;
    }

    @Override
    public int estimateCredit() {
        return matchedLecture.credit;
    }

    @Override
    public Snapshot takeSnapshot() {
        return new LectureSnapshot(
                new LectureCriteria(this),
                matchedLecture != null ? new LectureData(matchedLecture) : null,
                holding
        );
    }

    public Lecture getMatchedLecture() {
        return matchedLecture;
    }
}
