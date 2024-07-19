package org.konkuk.degreeverifier.business.csv;

import java.util.*;

public class Commit {
    public final static String[] HEADER = {
            // microdegree info
            "개정차수", "마이크로디그리명", "이수 학점",
            // student info
            "소속 대학명", "소속 학과", "성명", "학번", "학년", "생년월일", "등록학기수",
            // lecture info
            "과목명1", "학점1",
            "과목명2", "학점2",
            "과목명3", "학점3",
            "과목명4", "학점4",
            "과목명5", "학점5"
    };

    public enum ColumnName {
        VERSION("개정차수"),
        DEGREE_NAME("마이크로디그리명"),
        TOTAL_CREDIT("이수 학점"),

        UNIVERSITY("소속 대학명"),
        DEPARTMENT("소속 학과"),
        STUDENT_NAME("성명"),
        STUDENT_ID("학번"),
        STUDENT_YEAR("학년"),
        BIRTH("생년월일"),
        REGISTERED_SEMESTER("등록학기수"),

        COURSE_1("과목명1"),
        COURSE_CREDIT_1("학점1"),
        COURSE_2("과목명2"),
        COURSE_CREDIT_2("학점2"),
        COURSE_3("과목명3"),
        COURSE_CREDIT_3("학점3"),
        COURSE_4("과목명4"),
        COURSE_CREDIT_4("학점4"),
        COURSE_5("과목명5"),
        COURSE_CREDIT_5("학점5");

        public final String value;

        ColumnName(String value) {
            this.value = value;
        }

        public static List<String> getNames() {
            List<String> names = new LinkedList<>();
            for (ColumnName header : ColumnName.values()) {
                names.add(header.value);
            }
            return names;
        }
    }

    public static boolean isValidHeader(Collection<String> header) {
        return new HashSet<>(header).containsAll(
                ColumnName.getNames()
        );
    }

    public static Map<ColumnName, Integer> getColumnIndexMap(List<String> header) {
        Map<ColumnName, Integer> map = new HashMap<>();
        for (ColumnName name: ColumnName.values()) {
            map.put(name, header.indexOf(name.value));
        }
        return map;
    }
}
