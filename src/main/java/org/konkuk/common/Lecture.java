package org.konkuk.common;

/**
 * 이 클래스 객체는 사용자가 수강한 교과목 정보와 검사 도중의 참조 여부를 저장합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:45:31.013Z
 */
public class Lecture extends LectureData {
    /**
     * 주어진 상태에서 이 교과목이 일치를 위해 참조되었는지를 나타냅니다.
     */
    private boolean used = false;

    public Lecture(String year, String semester, String classification, String code, String name, int credit, String grade, String university) {
        super(year, semester, classification, code, name, credit, grade, university);
    }

    public void use() {
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    public void clear() {
        used = false;
    }
}
