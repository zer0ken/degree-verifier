package org.konkuk.common.verifier;

import org.konkuk.common.FileUtil;
import org.konkuk.common.FileUtilTest;
import org.konkuk.common.criteria.DegreeCriteria;

import static org.junit.jupiter.api.Assertions.*;

class DegreeVerifierTest extends FileUtilTest {
    DegreeVerifier degreeVerifier1 = new DegreeVerifier(FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(DegreeVerifierTest.class, "2024 실감미디어기획.json"),
            DegreeCriteria.class
    ));
    DegreeVerifier degreeVerifier2 = new DegreeVerifier(FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(DegreeVerifierTest.class, "2024 실감미디어펀더멘털.json"),
            DegreeCriteria.class
    ));
}