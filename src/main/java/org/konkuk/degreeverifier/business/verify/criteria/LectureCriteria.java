package org.konkuk.degreeverifier.business.verify.criteria;

import com.google.gson.annotations.SerializedName;

/**
 * 이 클래스는 교과목의 이수 여부를 판단하는 검사 기준의 정의를 나타냅니다.
 * 이 클래스 객체는 JSON 형식으로 전환될 수 있습니다.
 *
 * @author 이현령
 * @since 2024-05-24T15:56:16.565Z
 */
public class LectureCriteria {
    public static final String DEFAULT_MINIMUM_GRADE = "D-";
    public static final Boolean DEFAULT_NON_EXCLUSIVE = true;

    /**
     * 대상 교과목의 이름입니다.
     */
    @SerializedName("name")
    public final String lectureName;

    /**
     * 대상 교과목에서 받아야 하는 성적의 최소값을 나타내는 문자열입니다.
     * "A", "B", "C", "D"가 사용되면 바로 뒤에 "+"나 "-"가 올 수 있고, "F"는 단독으로만 사용됩니다.
     * 이 값이 명시되지 않으면 기본 값은 "D-"입니다.
     */
    @SerializedName("min_grade")
    protected final String minimumGrade;

    /**
     * 이 검사의 대상 교과목이 다른 검사에 중복으로 사용될 수 있는지를 나타냅니다.
     * 이 값이 명시되지 않으면 기본값은 true입니다.
     */
    @SerializedName("non_exclusive")
    protected final Boolean nonExclusive;

    public String getMinimumGrade() {
        return minimumGrade == null ? DEFAULT_MINIMUM_GRADE : minimumGrade;
    }

    public boolean isNonExclusive() {
        return nonExclusive == null ? DEFAULT_NON_EXCLUSIVE : nonExclusive;
    }

    public LectureCriteria(LectureCriteria toCopy) {
        lectureName = toCopy.lectureName;
        minimumGrade = toCopy.minimumGrade;
        nonExclusive = toCopy.nonExclusive;
    }
}
