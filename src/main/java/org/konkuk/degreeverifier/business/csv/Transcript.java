package org.konkuk.degreeverifier.business.csv;

import java.util.*;

public class Transcript {
    public enum ColumnName {
        COURSE_YEAR("학년도"),
        SEMESTER("학기"),
        REF_NO("학수번호"),
        CODE("교과목번호"),
        COURSE_NAME("교과목명"),
        CLASSIFICATION("이수구분"),
        CREDIT("학점"),
        GRADE("성적"),

        CAMPUS("캠퍼스"),
        UNIVERSITY("대학"),
        DEPARTMENT("학부(과)/전공"),
        STUDENT_ID("학번"),
        GENDER("성별"),
        STUDENT_NAME("성명"),
        STUDENT_YEAR("학년"),
        BIRTH("생년월일"),
        REGISTERED_SEMESTER("등록학기수");
        
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
