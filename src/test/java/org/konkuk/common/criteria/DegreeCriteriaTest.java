package org.konkuk.common.criteria;

import org.junit.jupiter.api.Test;
import org.konkuk.common.FileUtil;
import org.konkuk.common.verify.criteria.DegreeCriteria;

import static org.junit.jupiter.api.Assertions.*;

class DegreeCriteriaTest {
    DegreeCriteria degreeCriteria = FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(DegreeCriteriaTest.class, "DegreeCriteriaExample.json"),
            DegreeCriteria.class
    );

    @Test
    void load_correct_fields_from_json() {
        assertEquals("실감미디어펀더멘털", degreeCriteria.degreeName);
        assertEquals(12, degreeCriteria.minimumCredit);
        assertNotNull(degreeCriteria.recursiveCriteria);
    }
}