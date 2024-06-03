package org.konkuk.degreeverifier.business.student;

public class LectureData {
    public final String year;
    public final String semester;
    public final String classification;
    public final String code;
    public final String name;
    public final int credit;
    public final String grade;

    public final String university;

    public LectureData(String year, String semester, String classification, String code, String name, int credit, String grade, String university) {
        this.year = year;
        this.semester = semester;
        this.classification = classification;
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
        this.university = university;
    }

    public LectureData(LectureData toCopy) {
        this(
                toCopy.year,
                toCopy.semester,
                toCopy.classification,
                toCopy.code,
                toCopy.name,
                toCopy.credit,
                toCopy.grade,
                toCopy.university
        );
    }

    public Object[] inRow() {
        return new Object[]{
                year,
                semester,
                name,
                classification,
                credit,
                grade,
                university,
                code,
        };
    }

    public static Object[] getColumns() {
        return new Object[]{
                "년도",
                "학기",
                "과목명",
                "이수구분",
                "학점",
                "성적",
                "제공",
                "과목코드",
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
