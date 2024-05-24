package org.konkuk.common.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LectureCriteriaDataTest {
    LectureCriteriaData data1 = GsonUtil.loadJsonResource("LectureCriteriaExample.json", LectureCriteriaData.class);

    @Test
    void load_correct_fields_from_json() {
        assertEquals("XR비즈니스입문", data1.lectureName);
        assertNull(data1.minimumGrade);
        assertNull(data1.nonExclusive);
    }

    @Test
    void getter_returns_default_value_for_null_field() {
        assertEquals(LectureCriteriaData.DEFAULT_MINIMUM_GRADE, data1.getMinimumGrade());
        assertEquals(LectureCriteriaData.DEFAULT_NON_EXCLUSIVE, data1.isNonExclusive());
    }
}