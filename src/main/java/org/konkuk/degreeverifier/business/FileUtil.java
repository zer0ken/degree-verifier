package org.konkuk.degreeverifier.business;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.konkuk.degreeverifier.business.verify.SnapshotBundle;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    synchronized public static <T> T fromJsonFile(String fileName, Class<T> classOfT) {
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

    synchronized public static <T> void toJsonFile(Object object, String directory, String name, Class<T> classOfT) {
        String extension = ".json";
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        Path dirPath = Paths.get(directory);
        Path filePath = Paths.get(directory + name + extension);
        try {
            int exists = 2;
            while (Files.exists(filePath)) {
                filePath = Paths.get(directory + name + "(" + exists++ + ")" + extension);
            }
            String json = gson.toJson(object, classOfT);
            if (json == null) {
                return;
            }
            if (Files.notExists(dirPath)){
                Files.createDirectory(dirPath);
            }
            Files.createFile(filePath);
            Files.write(filePath, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static List<String[]> fromTsvFile(String fileName) {
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

    synchronized public static void exportCommit(String fileName, String content) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("Exception occurred while writing TXT file: " + fileName);
            throw new RuntimeException(e);
        }
    }

    synchronized public static SnapshotBundle loadCommit(File file) {
        SnapshotBundle bundle = new SnapshotBundle();
        try {
            Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).stream()
                    .filter(line -> !line.trim().isEmpty()).forEach(line -> bundle.put(line.trim(), null));
        } catch (IOException e) {
            System.err.println("Exception occurred while reading TXT file: " + file.getName());
            throw new RuntimeException(e);
        } finally {
            return bundle;
        }
    }

    /**
     * 이 메소드는 테스트 목적으로만 사용됩니다.
     */
    synchronized public static <T> String getAbsolutePathOfResource(Class<T> requester, String resourceName) {
        String asciiFileName = requester.getResource(resourceName).getFile();
        try {
            String utf8FileName = URLDecoder.decode(asciiFileName, "UTF-8");
            return (new File(URLDecoder.decode(utf8FileName, "UTF-8"))).getPath();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
