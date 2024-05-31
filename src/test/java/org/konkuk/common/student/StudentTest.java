package org.konkuk.common.student;

import com.sun.media.sound.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.DefaultPaths;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    final Student student = new Student(DefaultPaths.STUDENTS_PATH + "/201911205 - 이현령");

    StudentTest() throws InvalidFormatException {
    }

    @Test
    void not_loaded_Student() {
        assertEquals("이현령", student.name);
        assertEquals("201911205", student.id);
        assertFalse(student.isLoaded());
        assertFalse(student.isVerified());
    }

    @Test
    void loaded_and_not_verified_Student() {
        student.loadLectures(new ProgressTracker("test"));
        assertTrue(student.isLoaded());
        assertEquals(17, student.size());
    }
}