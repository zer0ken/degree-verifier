package org.konkuk.degreeverifier.business.student;

public class LectureData {
    public final String year;
    public final String semester;
    public final String name;
    public final int credit;
    public final String grade;

    public LectureData(String year, String semester, String name, int credit, String grade) {
        this.year = year;
        this.semester = semester;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }

    public LectureData(LectureData toCopy) {
        this(
                toCopy.year,
                toCopy.semester,
                toCopy.name,
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
