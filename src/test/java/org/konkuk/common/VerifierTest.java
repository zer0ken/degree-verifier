package org.konkuk.common;

import org.junit.jupiter.api.Test;
import org.konkuk.common.verify.Verifier;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VerifierTest {
    Verifier manager = new Verifier();

    @Test
    void loadVerifier_correctly_load_verifier_object() {
        manager.loadVerifier(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/2024 실감미디어기획.json"));
        assertEquals(1, manager.getDegreeVerifiers().size());
    }

    @Test
    void loadAllVerifier_correctly_load_all_verifier_objects() {
        manager.loadAllVerifiers(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/"));
        assertEquals(26, manager.getDegreeVerifiers().size());
    }

    @Test
    void loadLectures_correctly_load_all_lecture_ojects() {
        manager.loadLectures(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "LecturesExample1.tsv"));
        assertEquals(39, manager.getLectures().size());
    }

    @Test
    void verify_without_verifier_throws_RuntimeException() {
        manager.loadLectures(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "LecturesExample1.tsv"));
        assertThrows(RuntimeException.class, () -> manager.verify());
    }

    @Test
    void verify_without_lectures_throws_RuntimeException() {
        manager.loadAllVerifiers(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/"));
        assertThrows(RuntimeException.class, () -> manager.verify());
    }

    @Test
    void verify_LecturesExample1_should_detect_0_verified_degree() {
        manager.loadAllVerifiers(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/"));
        manager.loadLectures(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "LecturesExample1.tsv"));
        manager.verify();
        assertEquals(0, manager.getVerifiedDegreeMap().size());
    }

    @Test
    void verify_LectureExample2_should_detect_4_verified_degree() {
        manager.loadVerifier(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/2024 실감미디어펀더멘털.json"));
        manager.loadVerifier(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/2024 게임공학.json"));
        manager.loadVerifier(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/2024 게임기획.json"));
        manager.loadVerifier(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/2024 XR입출력기술.json"));

        manager.loadLectures(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "LecturesExample2.tsv"));

        manager.verify();
        assertTrue(manager.getVerifiedDegreeMap().containsKey("TTTT"));
    }

    @Test
    void verify_LectureExample2_should_detect_4_verified_degree_out_of_all_verifiers() {
        manager.loadAllVerifiers(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "verifier/"));
        manager.loadLectures(FileUtil.getAbsolutePathOfResource(VerifierTest.class, "LecturesExample2.tsv"));

        manager.verify();
        for (Map.Entry<String, List<DegreeSnapshot>> entry : manager.getVerifiedDegreeMap().entrySet()) {
            assertEquals(4, entry.getValue().size());
            return;
        }
    }
}