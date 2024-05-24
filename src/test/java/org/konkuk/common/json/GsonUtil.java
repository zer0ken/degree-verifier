package org.konkuk.common.json;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GsonUtil {
    public static <T> T loadJsonResource(String resourceName, Class<T> classOfT) {
        Gson gson = new Gson();
        try {
            Path path = Paths.get(GsonUtil.class.getResource(resourceName).toURI());
            return gson.fromJson(new String(Files.readAllBytes(path)), classOfT);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
