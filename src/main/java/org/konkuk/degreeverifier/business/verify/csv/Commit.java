package org.konkuk.degreeverifier.business.verify.csv;

import java.util.Arrays;

public class Commit {
    public final static String[] HEADER = {
            // student info
            "성명", "학번", "소속 대학명",
            // microdegree info
            "차수", "마이크로디그리명", "이수 학점",
            // lecture info
            "과목명1", "학점1",
            "과목명2", "학점2",
            "과목명3", "학점3",
            "과목명4", "학점4"
    };

    public static boolean isValidHeader(String[] header) {
        return Arrays.asList(HEADER).equals(Arrays.asList(header));
    }
}
