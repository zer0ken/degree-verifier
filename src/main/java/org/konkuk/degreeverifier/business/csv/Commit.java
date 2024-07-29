package org.konkuk.degreeverifier.business.csv;

import java.util.*;

public class Commit {
    public enum ColumnName {
        VERSION("개정차수"),
        DEGREE_NAME("마이크로디그리명"),

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
        COURSE_CREDIT_5("학점5"),

        TOTAL_CREDIT("이수 학점"),
        REQUIRED_CREDIT("이수 요구 학점"),
        VERIFIED("이수 여부");

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
        List<String> columnNames = ColumnName.getNames();
        columnNames.remove(ColumnName.REQUIRED_CREDIT.value);
        columnNames.remove(ColumnName.VERIFIED.value);
        return new HashSet<>(header).containsAll(
                columnNames
        );
    }

    public static Map<ColumnName, Integer> getColumnIndexMap(List<String> header) {
        Map<ColumnName, Integer> map = new HashMap<>();
        int nextIndex = header.size();
        for (ColumnName name: ColumnName.values()) {
            if (header.contains(name.value)) {
                map.put(name, header.indexOf(name.value));
            } else {
                map.put(name, nextIndex++);
            }
        }
        return map;
    }
}
