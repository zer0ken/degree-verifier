package org.konkuk.degreeverifier.business;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DefaultPaths {
    public static final String ROOT_PATH;

    static {
        try {
            ROOT_PATH = new File(
                    URLDecoder.decode(
                            DefaultPaths.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
                            "UTF-8"
                    )
            ).getParent();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String VERIFIERS_PATH = ROOT_PATH + "/검사 기준/";
    public static final String TRANSCRIPT_PATH = ROOT_PATH + "/성적표/";
    public static final String COMMIT_PATH = ROOT_PATH + "/이수 학위/";
    public static final String ALIASES_PATH = ROOT_PATH + "/동일 교과 목록/";
}
