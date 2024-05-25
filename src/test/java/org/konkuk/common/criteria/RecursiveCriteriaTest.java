package org.konkuk.common.criteria;

import org.junit.jupiter.api.Test;
import org.konkuk.common.FileUtil;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveCriteriaTest {
    RecursiveCriteria recursiveCriteria = FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(RecursiveCriteriaTest.class, "CriteriaExample.json"),
            RecursiveCriteria.class
    );

    @Test
    void load_correct_fields_from_json() {
        assertEquals("최소 3개 영역의 교과목 1개 이상 필수 이수", recursiveCriteria.label);
        assertEquals(3, recursiveCriteria.getMinimumPass());
        assertEquals(4, recursiveCriteria.subcriteria.length);

        assertNull(recursiveCriteria.lectureCriteria);

        assertFalse(recursiveCriteria.isImportant());
        assertFalse(recursiveCriteria.needsAllPass());
    }

    @Test
    void subcriteria_have_correct_fields() {
        RecursiveCriteria subcriteria = recursiveCriteria.subcriteria[3];

        assertEquals("비즈니스 영역(택 1~2)", subcriteria.label);
        assertEquals(1, subcriteria.getMinimumPass());
        assertEquals(3, subcriteria.subcriteria.length);

        assertTrue(subcriteria.isImportant());

        assertNull(subcriteria.lectureCriteria);

        assertFalse(recursiveCriteria.needsAllPass());
    }

    @Test
    void terminal_criteria_have_only_lectureCriteria_field() {
        RecursiveCriteria terminalRecursiveCriteria = recursiveCriteria.subcriteria[3].subcriteria[0];

        assertNotNull(terminalRecursiveCriteria.lectureCriteria);

        assertNull(terminalRecursiveCriteria.subcriteria);
        assertNull(terminalRecursiveCriteria.label);

        assertFalse(terminalRecursiveCriteria.isImportant());
        assertEquals(0, terminalRecursiveCriteria.getMinimumPass());

        assertFalse(recursiveCriteria.needsAllPass());
    }
}