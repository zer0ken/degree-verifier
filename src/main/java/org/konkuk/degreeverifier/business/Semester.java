package org.konkuk.degreeverifier.business;

public class Semester implements Comparable<Semester> {
    public enum Type {
        FIRST("1학기"),
        SUMMER("하계학기"),
        SECOND("2학기"),
        WINTER("동계학기");

        public final String value;

        Type(String value) {
            this.value = value;
        }

        public static Type fromString(String value) {
            for (Type type : Type.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            return null;
        }

        public static Type fromOrdinal(int ordinal) {
            for (Type type : Type.values()) {
                if (type.ordinal() == ordinal % 4) {
                    return type;
                }
            }
            return null;
        }
    }

    public final int year;
    public final Type semester;

    public Semester(int year, String semester) {
        this.year = year;
        this.semester = Type.fromString(semester);
    }

    public Semester(int year, Type semester) {
        this.year = year;
        this.semester = semester;
    }

    public Semester next() {
        int nextYear = year;
        if (semester == Type.WINTER) {
            nextYear++;
        }
        return new Semester(nextYear, Type.fromOrdinal(semester.ordinal() + 1));
    }

    @Override
    public int compareTo(Semester o) {
        if (year != o.year) {
            return year - o.year;
        }
        return semester.compareTo(o.semester);
    }

    @Override
    public String toString() {
        return year + "년도 " + semester.value;
    }
}
