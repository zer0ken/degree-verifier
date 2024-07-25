package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.Grade;
import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.student.Lecture;
import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * 이 클래스는 주어진 상태에서 특정 교과목을 이수했는지 검증합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:39:55.673Z
 */
public class LectureVerifier extends LectureCriteria implements Estimable, Snapshotable {
    public final Semester minSemester;
    public final Semester maxSemester;
    public final Grade minGrade;

    private Lecture matchedLecture = null;

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
        if (!lecture.name.equalsIgnoreCase(lectureName)) {
            boolean aliasFound = false;
            for (Set<String> alias : VerifierFactory.getInstance().getAliases()) {
                if (alias.contains(lecture.name.toLowerCase()) && alias.contains(lectureName.toLowerCase())) {
                    aliasFound = true;
                    break;
                }
            }
            if (!aliasFound) {
                return false;
            }
        }
        if (lecture.year != null && lecture.semester != null) {
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
        }
        if (lecture.grade != null) {
            Grade lectureGrade = Grade.fromString(lecture.grade);
            lectureGrade = lectureGrade != null ? lectureGrade : Grade.A_PLUS;
            if (getMinimumGrade().compareTo(lectureGrade) > 0) {
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

    public boolean isHolding() {
        return holding;
    }

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
