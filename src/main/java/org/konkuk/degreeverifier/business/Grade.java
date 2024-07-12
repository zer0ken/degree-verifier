package org.konkuk.degreeverifier.business;

public enum Grade {
    NON_PASS("N"),
    F("F"),
    D_MINUS("D-"),
    D_ZERO("D0"),
    D_PLUS("D+"),
    C_MINUS("C-"),
    C_ZERO("C0"),
    C_PLUS("C+"),
    B_MINUS("B-"),
    B_ZERO("B0"),
    B_PLUS("B+"),
    A_MINUS("A-"),
    A_ZERO("A0"),
    A_PLUS("A+"),
    PASS("P");

    public final String value;

    Grade(String value) {
        this.value = value;
    }

    public static Grade fromString(String value) {
        for (Grade type : Grade.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    public static Grade fromOrdinal(int ordinal) {
        for (Grade type : Grade.values()) {
            if (type.ordinal() == ordinal % Grade.values().length) {
                return type;
            }
        }
        return null;
    }
}