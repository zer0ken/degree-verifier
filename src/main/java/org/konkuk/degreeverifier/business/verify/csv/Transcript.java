package org.konkuk.degreeverifier.business.verify.csv;

import java.util.Arrays;

public class Transcript {
    public final static String[] HEADER = {
            // lecture info
            "학년도", "학기", "학수번호", "교과목번호", "교과목명",
            // student info
            "캠퍼스", "대학", "학부(과)/전공", "학번", "성별", "성명", "학년",
            // lecture info
            "이수구분", "학점", "성적"
    };

    public static boolean isValidHeader(String[] header) {
        return Arrays.asList(HEADER).equals(Arrays.asList(header));
    }
}
