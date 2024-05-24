package org.konkuk.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {
    List<String[]> rawLectures = FileUtil.fromTsvFile(
            FileUtil.getAbsolutePathOfResource(FileUtilTest.class, "LecturesExample.tsv")
    );

    @Test
    public void fromTsvFile_returns_correct_value() {
        assertArrayEquals(
                new String[]{"2019", "1학기", "지교", "BBAA57369", "C프로그래밍", "3", "A+"},
                rawLectures.get(0)
        );
    }
}