package org.konkuk.degreeverifier.business.verify.criteria;

import com.google.gson.annotations.SerializedName;

/**
 * 이 클래스는 하나의 학위에 대한 검사 기준을 나타냅니다.
 * 이 클래스 객체는 JSON 형식으로 전환될 수 있습니다.
 *
 * @author 이현령
 * @since 2024-05-24T16:31:10.191Z
 */
public class DegreeCriteria {
    /**
     * 검사 기준에 대한 짧은 설명입니다.
     */
    @SerializedName("label")
    public final String description;

    /**
     * 이 학위의 이름입니다.
     */
    @SerializedName("degree_name")
    public final String degreeName;

    /**
     * 이 학위를 인정받기 위해 이수해야 하는 학점의 최소값을 나타냅니다.
     */
    @SerializedName("min_credit")
    public final Integer minimumCredit;

    /**
     * 이 학위를 인정받기 위해 통과해야 하는 검사 조건을 나타냅니다.
     */
    @SerializedName("criteria")
    public final RecursiveCriteria recursiveCriteria;

    public DegreeCriteria(DegreeCriteria toCopy) {
        description = toCopy.description;
        degreeName = toCopy.degreeName;
        recursiveCriteria = toCopy.recursiveCriteria;
        minimumCredit = toCopy.minimumCredit;
    }
}
