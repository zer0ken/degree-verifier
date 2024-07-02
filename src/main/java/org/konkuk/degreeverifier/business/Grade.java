package org.konkuk.degreeverifier.business;

public enum Grade {
    PASS("P"),
    A_PLUS("A+"),
    A_ZERO("A0"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B_ZERO("B0"),
    B_MINUS("B-"),
    C_PLUS("C+"),
    C_ZERO("C0"),
    C_MINUS("C-"),
    D_PLUS("D+"),
    D_ZERO("D0"),
    D_MINUS("D-"),
    F("F"),
    NON_PASS("N");

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