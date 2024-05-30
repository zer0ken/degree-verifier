package org.konkuk.common.student;

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
}
