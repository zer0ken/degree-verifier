package org.konkuk.degreeverifier.business.verify.criteria;

import com.google.gson.annotations.SerializedName;

/**
 * 이 클래스는 재귀적으로 하위 검사 기준을 가질 수 있는 복잡한 검사 그룹의 정의를 나타냅니다.
 * 이 클래스 객체는 JSON 형식으로 전환될 수 있습니다.
 *
 * @author 이현령
 * @since 2024-05-24T15:59:05.955Z
 */
public class RecursiveCriteria {
    /**
     * 검사 기준에 대한 짧은 설명입니다.
     */
    @SerializedName("label")
    public String description;

    /**
     * 검사 기준의 중요도를 나타냅니다. 이 값이 True이면 이 검사가 실패했을 때 이 검사를 포함하는 학위를 이수할 수 없습니다.
     */
    @SerializedName("important")
    public Boolean important;

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
     *     <li>subcriteria</li>
     * </ul>
     * </p>
     */
    @SerializedName("lecture")
    public LectureCriteria lectureCriteria;

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
    public Boolean needAllPass;

    /**
     * 하위 검사 기준 중 통과해야 하는 기준의 최소값을 의미합니다.
     * 이 값이 명시되면 통과한 하위 검사 기준의 개수가 이 값 이상이어야 이 검사를 통과합니다.
     * 이 값이 명시되지 않으면 기본값은 0입니다.
     */
    @SerializedName("min")
    public Integer minimumPass;

    /**
     * 학점 계산 시에 사용할 교과목 검사 기준의 개수를 의미합니다.
     * 이 값이 명시되지 않으면 기본값은 null입니다.
     */
    @SerializedName("max")
    public Integer maximumPass;

    /**
     * 하위 검사 기준의 배열입니다.
     */
    @SerializedName("subcriteria")
    public RecursiveCriteria[] subcriteria;

    public RecursiveCriteria(RecursiveCriteria toCopy) {
        description = toCopy.description;
        important = toCopy.important;
        lectureCriteria = toCopy.lectureCriteria;
        needAllPass = toCopy.needAllPass;
        minimumPass = toCopy.minimumPass;
        maximumPass = toCopy.maximumPass;
        subcriteria = toCopy.subcriteria;
    }

    public RecursiveCriteria(
            String description,
            Boolean important,
            LectureCriteria lectureCriteria,
            Boolean needAllPass,
            Integer minimumPass,
            Integer maximumPass,
            RecursiveCriteria[] subcriteria
    ) {
        this.description = description;
        this.important = important;
        this.lectureCriteria = lectureCriteria;
        this.needAllPass = needAllPass;
        this.minimumPass = minimumPass;
        this.maximumPass = maximumPass;
        this.subcriteria = subcriteria;
    }

    public Boolean isImportant() {
        return important != null && important;
    }

    public boolean needsAllPass() {
        return needAllPass != null && needAllPass;
    }

    public int getMinimumPass() {
        return minimumPass != null ? minimumPass : 0;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public RecursiveCriteria[] getSubcriteria() {
        return subcriteria;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (description != null) {
            sb.append(description).append(" | ");
        }
        if (isImportant()) {
            sb.append("필수 ");
        }
        if (lectureCriteria != null) {
            sb.append("교과목: ").append(lectureCriteria.lectureName);
        } else {
            sb.append("검사 그룹(").append(subcriteria.length).append(")");
        }
        return sb.toString();
    }

    public String getValidPassString() {
        String prefix = "필요 통과 수: ";
        if (needsAllPass()) {
            return prefix + "모든 하위 검사 통과";
        }
        String needPass = "";
        if (minimumPass != null) {
            needPass += minimumPass + " ~";
        }
        if ( maximumPass != null) {
            if (needPass.isEmpty()) {
                needPass = "~";
            }
            needPass += " " + maximumPass;
        }
        if (!needPass.isEmpty()) {
            return prefix + needPass;
        }
        return prefix + "제한 없음";
    }
}