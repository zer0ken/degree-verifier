package org.konkuk.degreeverifier.business.verify.csv;

import java.util.Arrays;

public class Transcript {
    public final static String[] HEADER = {
            // student info
            "성명", "학번", "소속 대학명",
            // lecture info
            "이수 년도", "이수 학기", "교과목명", "이수 학점", "이수 성적"
    };

    public static boolean isValidHeader(String[] header) {
        return Arrays.asList(HEADER).equals(Arrays.asList(header));
    }
}
