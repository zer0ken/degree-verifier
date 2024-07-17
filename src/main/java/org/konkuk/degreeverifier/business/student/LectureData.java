package org.konkuk.degreeverifier.business.student;

public class LectureData {
    public final String year;
    public final String semester;
    public final String referenceNumber;
    public final String code;
    public final String name;
    public final String classification;
    public final int credit;
    public final String grade;

    public LectureData(String year, String semester, String referenceNumber, String code, String name, String classification, int credit, String grade) {
        this.year = year;
        this.semester = semester;
        this.referenceNumber = referenceNumber;
        this.code = code;
        this.name = name;
        this.classification = classification;
        this.credit = credit;
        this.grade = grade;
    }

    public LectureData(LectureData toCopy) {
        this(
                toCopy.year,
                toCopy.semester,
                toCopy.referenceNumber,
                toCopy.code,
                toCopy.name,
                toCopy.classification,
                toCopy.credit,
                toCopy.grade
        );
    }

    public Object[] inRow() {
        return new Object[]{
                year,
                semester,
                name,
                credit,
                grade
        };
    }

    public static Object[] getColumns() {
        return new Object[]{
                "이수 년도",
                "이수 학기",
                "교과목명",
                "이수 학점",
                "이수 성적"
        };
    }

    @Override
    public int hashCode() {
        return (year+semester+name).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
