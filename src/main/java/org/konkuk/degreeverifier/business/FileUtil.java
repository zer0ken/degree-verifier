package org.konkuk.degreeverifier.business;

import com.google.gson.Gson;

import java.io.*;
import java.net.URLDecoder;
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
        } catch (Exception e) {
            System.err.println("Exception occurred while reading json file: " + fileName);
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
        } catch (Exception e) {
            System.err.println("Exception occurred while reading TSV file: " + fileName);
            throw new RuntimeException(e);
        }
        return tokenizedLines;
    }

    /**
     * 이 메소드는 테스트 목적으로만 사용됩니다.
     */
    public static <T> String getAbsolutePathOfResource(Class<T> requester, String resourceName) {
        String asciiFileName = requester.getResource(resourceName).getFile();
        try {
            String utf8FileName = URLDecoder.decode(asciiFileName, "UTF-8");
            return (new File(URLDecoder.decode(utf8FileName, "UTF-8"))).getPath();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
