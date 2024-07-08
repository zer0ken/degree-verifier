package org.konkuk.degreeverifier.business.verify.criteria;

import com.google.gson.annotations.SerializedName;
import org.konkuk.degreeverifier.business.Semester;

/**
 * 이 클래스는 하나의 학위에 대한 검사 기준을 나타냅니다.
 * 이 클래스 객체는 JSON 형식으로 전환될 수 있습니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:31:10.191Z
 */
public class DegreeCriteria {
    /**
     * 이 학위의 이름입니다.
     */
    @SerializedName("degree_name")
    public String degreeName;

    /**
     * 이 학위의 개정 차수(버전)입니다.
     */
    @SerializedName("version")
    public Integer version;

    /**
     * 검사 기준에 대한 짧은 설명입니다.
     */
    @SerializedName("label")
    public String description;

    /**
     * 이 학위를 인정받기 위해 이수해야 하는 학점의 최소값을 나타냅니다.
     */
    @SerializedName("min_credit")
    public Integer minimumCredit;

    /**
     * 이 학위의 하위 교과목 검사의 유효 이수 년도가 언제 시작되는지 나타냅니다.
     * 이 값이 명시되면 minimumSemester 또한 명시되어야 합니다.
     */
    @SerializedName("from_year")
    public Integer minimumYear;

    /**
     * 이 학위의 하위 교과목 검사의 유효 이수 학기가 언제 시작되는지 나타냅니다.
     * minimumYear가 명시되지 않으면 이 값은 무시됩니다.
     */
    @SerializedName("from_semester")
    public String minimumSemester;

    /**
     * 이 학위의 하위 교과목 검사의 유효 이수 년도가 언제 끝나는지 나타냅니다.
     * 이 값이 명시되면 minimumSemester 또한 명시되어야 합니다.
     */
    @SerializedName("to_year")
    public Integer maximumYear;

    /**
     * 이 학위의 하위 교과목 검사의 유효 이수 학기가 언제 끝나는지 나타냅니다.
     * maximumYear가 명시되지 않으면 이 값은 무시됩니다.
     */
    @SerializedName("to_semester")
    public String maximumSemester;

    /**
     * 이 학위를 인정받기 위해 통과해야 하는 검사 조건을 나타냅니다.
     */
    @SerializedName("criteria")
    public RecursiveCriteria recursiveCriteria;

    public DegreeCriteria(DegreeCriteria toCopy) {
        degreeName = toCopy.degreeName;
        version = toCopy.version;
        description = toCopy.description;
        minimumCredit = toCopy.minimumCredit;
        recursiveCriteria = toCopy.recursiveCriteria;
        minimumYear = toCopy.minimumYear;
        minimumSemester = toCopy.minimumSemester;
        maximumYear = toCopy.maximumYear;
        maximumSemester = toCopy.maximumSemester;
    }

    public DegreeCriteria(
            String degreeName,
            Integer version,
            String description,
            Integer minimumCredit,
            Integer minimumYear,
            Integer maximumYear,
            String minimumSemester,
            String maximumSemester,
            RecursiveCriteria recursiveCriteria
    ) {
        this.degreeName = degreeName;
        this.version = version;
        this.description = description;
        this.minimumCredit = minimumCredit;
        this.minimumYear = minimumYear;
        this.minimumSemester = minimumSemester;
        this.maximumYear = maximumYear;
        this.maximumSemester = maximumSemester;
        this.recursiveCriteria = recursiveCriteria;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    @Override
    public String toString() {
        return "개정" + version + "차 " + degreeName;
    }

    public String getValidCreditString() {
        return "필요 학점: " + minimumCredit + " ~";
    }

    public Semester getMinimumSemester() {
        if (minimumYear != null) {
            return new Semester(minimumYear, Semester.Type.fromString(minimumSemester));
        }
        return null;
    }

    public Semester getMaximumSemester() {
        if (maximumYear != null) {
            return new Semester(maximumYear, Semester.Type.fromString(maximumSemester));
        }
        return null;
    }

    public String getValidPeriodString() {
        return Semester.buildValidPeriodString(getMinimumSemester(), getMaximumSemester());
    }
}
