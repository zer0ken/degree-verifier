package org.konkuk.common;

public class Lecture {
    public final int year;
    public final int semester;
    public final String classification;
    public final String code;
    public final String name;
    public final int credit;
    public final String grade;

    public Lecture(int year, int semester, String classification, String code, String name, int credit, String grade) {
        this.year = year;
        this.semester = semester;
        this.classification = classification;
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }
}
