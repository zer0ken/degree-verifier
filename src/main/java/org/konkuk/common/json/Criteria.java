package org.konkuk.common.json;

import com.google.gson.annotations.SerializedName;

/**
 * 이 클래스는 하위 검사 기준을 가질 수 있는 복잡한 검사 기준의 정의를 나타냅니다.
 * 이 클래스 객체는 JSON 형식으로 전환될 수 있습니다.
 *
 * @author 이현령
 * @since 2024-05-24T15:59:05.955Z
 */
public class Criteria {
    /**
     * 검사 기준에 대한 짧은 설명입니다.
     */
    @SerializedName("label")
    protected String label;

    /**
     * 검사 기준의 중요도를 나타냅니다. 이 값이 True이면 이 검사가 실패했을 때 이 검사를 포함하는 학위를 이수할 수 없습니다.
     */
    @SerializedName("important")
    protected Boolean important;

    /**
     * <p>
     * 교과목 검사 기준을 나타냅니다.
     * 이 값이 null이 아니면 검사를 통과하기 위해 이 값에 저장된 교과목 검사 기준 또한 통과해야 합니다.
     * </p>
     * <p>
     * 이 값이 null이 아니면 다음의 필드 값은 무시됩니다.
     * <ul>
     *     <li>needAllPass</li>
     *     <li>minimumPass</li>
     * </ul>
     * </p>
     */
    @SerializedName("lecture")
    protected LectureCriteria lectureCriteria;

    /**
     * <p>
     * 모든 하위 검사 기준을 통과해야 하는지를 나타냅니다.
     * 이 값이 true이면 모든 하위 검사 기준을 통과해야 이 검사를 통과합니다.
     * </p>
     * <p>
     * 이 값이 true이면 다음의 필드 값은 무시됩니다.
     * <ul>
     *     <li>minimumPass</li>
     * </ul>
     * </p>
     */
    @SerializedName("all")
    protected Boolean needAllPass;

    /**
     * <p>
     * 하위 검사 기준 중 통과해야 하는 기준의 최소값을 의미합니다.
     * 이 값이 true이면 통과한 하위 검사 기준의 개수가 이 값 이상이어야 이 검사를 통과합니다.
     * </p>
     * <p>
     * 이 값이 true이면 다음의 필드 값은 무시됩니다.
     * <ul>
     *     <li>needAllPass</li>
     * </ul>
     * </p>
     */
    @SerializedName("min")
    protected Integer minimumPass;

    /**
     * 하위 검사 기준의 배열입니다.
     */
    @SerializedName("subcriteria")
    protected Criteria[] subcriteria;

    protected Criteria(Criteria toCopy) {
        label = toCopy.label;
        important = toCopy.important;
        lectureCriteria = toCopy.lectureCriteria;
        needAllPass = toCopy.needAllPass;
        minimumPass = toCopy.minimumPass;
        subcriteria = toCopy.subcriteria;
    }
}