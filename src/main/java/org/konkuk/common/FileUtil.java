package org.konkuk.common;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 파일로부터 데이터를 불러오는 함수들을 묶어둔 클래스입니다.
 *
 * @author 이현령
 * @since 2024-05-24T23:23:06.064Z
 */
public class FileUtil {
    public static <T> T fromJsonFile(String fileName, Class<T> classOfT) {
        Gson gson = new Gson();
        Path path = Paths.get(fileName);
        try {
            String json = new String(Files.readAllBytes(path));
            return gson.fromJson(json, classOfT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String[]> fromTsvFile(String fileName) {
        List<String[]> tokenizedLines = new ArrayList<>();
        File file = new File(fileName);
        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis))
        ) {
            br.lines().forEach(line -> tokenizedLines.add(line.split("\t")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tokenizedLines;
    }

    public static <T> String getAbsolutePathOfResource(Class<T> requester, String resourceName) {
        return (new File(requester.getResource(resourceName).getFile())).getAbsolutePath();
    }
}
