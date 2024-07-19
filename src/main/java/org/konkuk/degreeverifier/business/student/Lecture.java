package org.konkuk.degreeverifier.business.student;

/**
 * 이 클래스 객체는 사용자가 수강한 교과목 정보와 검사 도중의 참조 여부를 저장합니다.
 *
 * @author 이현령
 * @since 2024-05-25T15:45:31.013Z
 */
public class Lecture extends LectureData {
    public Lecture(String year, String semester, String referenceNumber,
                   String code, String name, String classification,
                   int credit, String grade) {
        super(year, semester, referenceNumber, code, name, classification, credit, grade);
    }

    public Lecture(LectureData toCopy) {
        super(toCopy);
    }

    @Override
    public String toString() {
        return year + "-" + semester + "-" + name;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Lecture)) {
            return false;
        }
        return toString().equals(obj.toString());
    }
}
