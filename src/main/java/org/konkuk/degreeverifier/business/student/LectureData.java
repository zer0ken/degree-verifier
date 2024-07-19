package org.konkuk.degreeverifier.business.student;

import org.konkuk.degreeverifier.business.csv.Transcript;

public class LectureData {
    public final String year;
    public final String semester;
    public final String referenceNumber;
    public final String code;
    public final String name;
    public final String classification;
    public final Integer credit;
    public final String grade;

    public LectureData(String year, String semester, String referenceNumber, String code, String name, String classification, Integer credit, String grade) {
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
                referenceNumber,
                code,
                name,
                classification,
                credit,
                grade
        };
    }

    public static Object[] getColumns() {
        return new Object[]{
                Transcript.ColumnName.COURSE_YEAR.value,
                Transcript.ColumnName.SEMESTER.value,
                Transcript.ColumnName.REF_NO.value,
                Transcript.ColumnName.CODE.value,
                Transcript.ColumnName.COURSE_NAME.value,
                Transcript.ColumnName.CLASSIFICATION.value,
                Transcript.ColumnName.CREDIT.value,
                Transcript.ColumnName.GRADE.value
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
