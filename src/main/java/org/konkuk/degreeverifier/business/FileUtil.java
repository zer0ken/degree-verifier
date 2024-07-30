package org.konkuk.degreeverifier.business;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.konkuk.degreeverifier.business.csv.CsvExportable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.newBufferedReader;

/**
 * 파일로부터 데이터를 불러오는 함수들을 묶어둔 클래스입니다.
 *
 * @author 이현령
 * @since 2024-05-24T23:23:06.064Z
 */
public class FileUtil {
    public static final String UTF8_BOM = "\uFEFF";

    synchronized public static <T> T fromJsonFile(String fileName, Class<T> classOfT) {
        Gson gson = new Gson();
        Path path = Paths.get(fileName);
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
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
            if (Files.notExists(dirPath)) {
                Files.createDirectory(dirPath);
            }
            Files.createFile(filePath);
            Files.write(filePath, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static void toCsvFile(File file, List<String> header, Collection<? extends CsvExportable> data) {
        boolean exists = file.exists();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))
        ) {
            if (!exists) {
                writer.write(String.join(",", header) + "\n");
            }
            for (CsvExportable datum : data) {
                writer.write(datum.toCsv());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public static List<List<String>> fromCsvFile(File file) {
        List<List<String>> table = new LinkedList<>();
        boolean bomChecked = false;
        try (BufferedReader reader = newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                if (!bomChecked && line.startsWith(FileUtil.UTF8_BOM))
                    line = line.substring(1);
                bomChecked = true;
                List<String> tokens = new LinkedList<>(Arrays.asList(line.trim().split(",")));
                tokens.replaceAll(String::trim);
                table.add(tokens);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return table;
    }
}
