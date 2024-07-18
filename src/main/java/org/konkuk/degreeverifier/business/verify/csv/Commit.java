package org.konkuk.degreeverifier.business.verify.csv;

import java.util.Arrays;
import java.util.HashSet;

public class Commit {
    public final static String[] HEADER = {
            // microdegree info
            "개정차수", "마이크로디그리명", "이수 학점",
            // student info
            "소속 대학명", "소속 학과", "성명", "학번", "학년",
            // lecture info
            "과목명1", "학점1",
            "과목명2", "학점2",
            "과목명3", "학점3",
            "과목명4", "학점4"
    };

    public static boolean isValidHeader(String[] header) {
        return new HashSet<>(Arrays.asList(header)).containsAll(Arrays.asList(HEADER));
    }
}
