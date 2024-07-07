package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.Grade;
import org.konkuk.degreeverifier.business.Semester;
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
public class LectureVerifier extends LectureCriteria implements Creditizable, Estimable, Snapshotable {
    public final Semester minSemester;
    public final Semester maxSemester;
    public final Grade minGrade;

    private Lecture matchedLecture = null;  // TODO: 2024-05-31 한 교과목 검사 기준이 여러 교과목에 대해 매치할 수 있도록 수정할 필요가 있을까?

    private boolean holding = false;

    private String degreeName;
    private final List<String> duplicatedDegrees = new LinkedList<>();

    public LectureVerifier(LectureCriteria toCopy) {
        super(toCopy);
        minSemester = getMinimumSemester();
        maxSemester = getMaximumSemester();
        minGrade = getMinimumGrade();
    }

    public List<LectureVerifier> match(List<Lecture> lectures, Semester minimumSemester, Semester maximumSemester) {
        List<LectureVerifier> exclusiveLectureVerifiers = new LinkedList<>();
        for (Lecture lecture : lectures) {
            if (match(lecture, minimumSemester, maximumSemester)) {
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

    public boolean verify() {
        return holding;
    }

    private boolean match(Lecture lecture, Semester defaultMinimumSemester, Semester defaultMaximumSemester) {
        if (!lecture.name.equals(lectureName)) {
            return false;
        }
        Semester lectureSemester = new Semester(
                Integer.parseInt(lecture.year),
                lecture.semester
        );
        if (minSemester != null && minSemester.compareTo(lectureSemester) > 0) {
            return false;
        }
        if (maxSemester != null && maxSemester.compareTo(lectureSemester) < 0) {
            return false;
        }
        if (minSemester == null
                && defaultMinimumSemester != null
                && defaultMinimumSemester.compareTo(lectureSemester) > 0) {
            return false;
        }
        if (maxSemester == null
                && defaultMaximumSemester != null
                && defaultMaximumSemester.compareTo(lectureSemester) < 0) {
            return false;
        }
        Grade lectureGrade = Grade.fromString(lecture.grade);
        lectureGrade = lectureGrade != null ? lectureGrade : Grade.A_PLUS;
        return minGrade == null || minGrade.compareTo(lectureGrade) <= 0;
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
                holding,
                degreeName,
                duplicatedDegrees.toArray(new String[0])
        );
    }

    public Lecture getMatchedLecture() {
        return matchedLecture;
    }

    public void addDuplicatedDegree(String degreeName) {
        duplicatedDegrees.add(degreeName);
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getDegreeName() {
        return degreeName;
    }
}
