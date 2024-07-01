package org.konkuk.business.student;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.student.Lecture;

public class LectureTest {

    private Lecture lecture1;
    private Lecture lecture2;

    @BeforeEach
    void setUp() {
        lecture1 = new Lecture("2023", "1", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        lecture2 = new Lecture(lecture1);
    }

    @Test
    void testConstructor() {
        assertEquals("2023", lecture1.year);
        assertEquals("1", lecture1.semester);
        assertEquals("전공필수", lecture1.classification);
        assertEquals("CS101", lecture1.code);
        assertEquals("자료구조", lecture1.name);
        assertEquals(3, lecture1.credit);
        assertEquals("A+", lecture1.grade);
        assertEquals("건국대", lecture1.university);
    }

    @Test
    void testCopyConstructor() {
        assertEquals(lecture1, lecture2);
    }

    @Test
    void testToString() {
        assertEquals("2023-1-자료구조", lecture1.toString());
    }

    @Test
    void testHashCodeAndEquals() {
        Lecture lecture3 = new Lecture("2023", "1", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        assertEquals(lecture1.hashCode(), lecture3.hashCode());
        assertEquals(lecture1, lecture3);
    }

    @Test
    void testHashCodeWithDifferentValues() {
        Lecture lecture4 = new Lecture("2023", "2", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        assertNotEquals(lecture1.hashCode(), lecture4.hashCode());
    }

    @Test
    void testEqualsWithDifferentValues() {
        Lecture lecture4 = new Lecture("2023", "2", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        assertNotEquals(lecture1, lecture4);
    }
}
