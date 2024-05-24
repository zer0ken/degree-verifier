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
        assertEquals(3, recursiveCriteria.minimumPass);
        assertEquals(4, recursiveCriteria.subcriteria.length);

        assertNull(recursiveCriteria.important);
        assertNull(recursiveCriteria.lectureCriteria);

        assertFalse(recursiveCriteria.needsAllPass());
    }

    @Test
    void subcriteria_have_correct_fields() {
        RecursiveCriteria subcriteria = recursiveCriteria.subcriteria[3];

        assertEquals("비즈니스 영역(택 1~2)", subcriteria.label);
        assertEquals(true, subcriteria.important);
        assertEquals(1, subcriteria.minimumPass);
        assertEquals(3, subcriteria.subcriteria.length);

        assertNull(subcriteria.lectureCriteria);

        assertFalse(recursiveCriteria.needsAllPass());
    }

    @Test
    void terminal_criteria_have_only_lectureCriteria_field() {
        RecursiveCriteria terminalRecursiveCriteria = recursiveCriteria.subcriteria[3].subcriteria[0];

        assertNotNull(terminalRecursiveCriteria.lectureCriteria);

        assertNull(terminalRecursiveCriteria.subcriteria);
        assertNull(terminalRecursiveCriteria.label);
        assertNull(terminalRecursiveCriteria.important);
        assertNull(terminalRecursiveCriteria.minimumPass);

        assertFalse(recursiveCriteria.needsAllPass());
    }
}