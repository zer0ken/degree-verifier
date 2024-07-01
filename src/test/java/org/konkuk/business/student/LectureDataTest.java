package org.konkuk.business.student;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.student.LectureData;

public class LectureDataTest {
    private LectureData lectureData1;
    private LectureData lectureData2;

    @BeforeEach
    void setUp() {
        lectureData1 = new LectureData("2023", "1", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        lectureData2 = new LectureData(lectureData1);
    }

    @Test
    void testConstructor() {
        assertEquals("2023", lectureData1.year);
        assertEquals("1", lectureData1.semester);
        assertEquals("전공필수", lectureData1.classification);
        assertEquals("CS101", lectureData1.code);
        assertEquals("자료구조", lectureData1.name);
        assertEquals(3, lectureData1.credit);
        assertEquals("A+", lectureData1.grade);
        assertEquals("건국대", lectureData1.university);
    }

    @Test
    void testCopyConstructor() {
        assertEquals(lectureData1, lectureData2);
    }

    @Test
    void testInRow() {
        Object[] expectedRow = {"2023", "1", "자료구조", "전공필수", 3, "A+", "건국대", "CS101"};
        assertArrayEquals(expectedRow, lectureData1.inRow());
    }

    @Test
    void testGetColumns() {
        Object[] expectedColumns = {"년도", "학기", "과목명", "이수구분", "학점", "성적", "제공", "과목코드"};
        assertArrayEquals(expectedColumns, LectureData.getColumns());
    }

    @Test
    void testHashCodeAndEquals() {
        LectureData lectureData3 = new LectureData("2023", "1", "전공필수", "CS101", "자료구조", 3, "A+", "건국대");
        assertEquals(lectureData1.hashCode(), lectureData3.hashCode());
        assertEquals(lectureData1, lectureData3);
    }
}
