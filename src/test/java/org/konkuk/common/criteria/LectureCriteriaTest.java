package org.konkuk.common.criteria;

import org.junit.jupiter.api.Test;
import org.konkuk.common.FileUtil;

import static org.junit.jupiter.api.Assertions.*;

class LectureCriteriaTest {
    LectureCriteria lectureCriteria = FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(LectureCriteriaTest.class, "LectureCriteriaExample.json"),
            LectureCriteria.class
    );

    @Test
    void load_correct_fields_from_json() {
        assertEquals("XR비즈니스입문", lectureCriteria.lectureName);
        assertNull(lectureCriteria.minimumGrade);
        assertNull(lectureCriteria.nonExclusive);
    }

    @Test
    void getters_return_default_value_for_null_field() {
        assertEquals(LectureCriteria.DEFAULT_MINIMUM_GRADE, lectureCriteria.getMinimumGrade());
        assertEquals(LectureCriteria.DEFAULT_NON_EXCLUSIVE, lectureCriteria.isNonExclusive());
    }
}