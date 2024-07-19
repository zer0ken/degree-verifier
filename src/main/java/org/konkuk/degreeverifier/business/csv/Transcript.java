package org.konkuk.degreeverifier.business.csv;

import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.student.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiFunction;

import static java.nio.file.Files.newBufferedReader;

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

    private static final long FILE_SIZE_LIMIT = (long) 300000.0e+6;
//    private static final long FILE_SIZE_LIMIT = (long) 30.0e+6;
    private static final int STUDENT_PER_FILE = 10000;

    public static boolean isInvalidHeader(Collection<String> header) {
        return !new HashSet<>(header).containsAll(
                ColumnName.getNames()
        );
    }

    public static Map<ColumnName, Integer> getColumnIndexMap(List<String> header) {
        Map<ColumnName, Integer> map = new HashMap<>();
        for (ColumnName name : ColumnName.values()) {
            map.put(name, header.indexOf(name.value));
        }
        return map;
    }

    public static List<File> splitFile(File originalFile) {
        List<File> files = new LinkedList<>();
        try {
            long size = Files.size(originalFile.toPath());
            if (size < FILE_SIZE_LIMIT) {
                files.add(originalFile);
                return files;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int lineCount = 0;
        int studentCount = 0;
        int fileCount = 0;
        List<String> header = null;
        Map<ColumnName, Integer> nameToIndex = null;

        String fileName = originalFile.getAbsolutePath().replaceFirst("\\.csv$", ".frag%d.csv");

        BufferedWriter latestWriter = null;
        Map<String, BufferedWriter> studentToWriter = new TreeMap<>();

        boolean bomChecked = false;
        try (BufferedReader reader = newBufferedReader(originalFile.toPath(), StandardCharsets.UTF_8)) {
            for (; ; ) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (!bomChecked && line.startsWith(FileUtil.UTF8_BOM)) {
                    line = line.substring(1);
                }
                bomChecked = true;

                List<String> row = new LinkedList<>(Arrays.asList(line.trim().split(",")));
                row.replaceAll(String::trim);

                if (lineCount == 0) {
                    if (isInvalidHeader(row)) {
                        return null;
                    }
                    header = row;
                    nameToIndex = getColumnIndexMap(row);
                } else {
                    Map<ColumnName, Integer> finalNameToIndex = nameToIndex;
                    BiFunction<List<String>, ColumnName, String> get = (_row, columnName) ->
                            _row.get(finalNameToIndex.get(columnName));

                    String student = new Student(
                            get.apply(row, ColumnName.UNIVERSITY),
                            get.apply(row, ColumnName.STUDENT_NAME),
                            get.apply(row, ColumnName.STUDENT_ID)
                    ).toString();
                    if (!studentToWriter.containsKey(student)) {
                        if (latestWriter == null || studentCount % STUDENT_PER_FILE == 0) {
                            File newFile = new File(String.format(fileName, ++fileCount));
                            Files.deleteIfExists(newFile.toPath());
                            try {
                                latestWriter = new BufferedWriter(
                                        new OutputStreamWriter(
                                                new FileOutputStream(newFile)
                                        )
                                );
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            files.add(newFile);
                            latestWriter.write(String.join(",", header) + "\n");
                        }
                        studentToWriter.put(student, latestWriter);
                        studentCount++;
                    }
                    studentToWriter.get(student).write(
                            String.join(",", row) + "\n"
                    );
                }

                lineCount++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (BufferedWriter writer : new HashSet<>(studentToWriter.values())) {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return files;
    }
}
