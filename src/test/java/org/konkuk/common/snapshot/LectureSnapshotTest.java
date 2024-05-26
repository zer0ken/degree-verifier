package org.konkuk.common.snapshot;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.konkuk.common.FileUtil;
import org.konkuk.common.FileUtilTest;
import org.konkuk.common.Lecture;
import org.konkuk.common.criteria.LectureCriteria;
import org.konkuk.common.verifier.LectureVerifier;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectureSnapshotTest extends FileUtilTest {
    LectureVerifier verifier1 = new LectureVerifier(FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(LectureSnapshotTest.class, "LectureCriteriaExample.json"),
            LectureCriteria.class
    ));
    LectureVerifier verifier2 = new LectureVerifier(FileUtil.fromJsonFile(
            FileUtil.getAbsolutePathOfResource(LectureSnapshotTest.class, "LectureCriteriaExample2.json"),
            LectureCriteria.class
    ));

    @Nested
    class before_match{
        @Test
        void takeSnapshot_of_verifier1_returns_correct_snapshot_object() {
            LectureSnapshot snapshot = (LectureSnapshot) verifier1.takeSnapshot();

            assertEquals("XR비즈니스입문", snapshot.criteria.lectureName);
            assertTrue(snapshot.criteria.isNonExclusive());
            assertEquals(LectureCriteria.DEFAULT_MINIMUM_GRADE, snapshot.criteria.getMinimumGrade());

            assertNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }

        @Test
        void takeSnapshot_of_verifier2_returns_correct_snapshot_object() {
            LectureSnapshot snapshot = (LectureSnapshot) verifier2.takeSnapshot();

            assertEquals("XR비즈니스입문", snapshot.criteria.lectureName);
            assertFalse(snapshot.criteria.isNonExclusive());
            assertEquals("F", snapshot.criteria.getMinimumGrade());

            assertNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }
    }

    @Nested
    class after_match {
        @Test
        void snapshot_after_match_failed_on_verifier1_has_null_matched_and_false_verified() {
            List<Lecture> lectures = new LinkedList<>();
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문ㅋ", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문2", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스개론", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스및벤처", 3, "D", ""));

            verifier1.match(lectures);

            LectureSnapshot snapshot = (LectureSnapshot) verifier1.takeSnapshot();

            assertNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }

        @Test
        void snapshot_after_match_failed_on_verifier2_has_null_matched_and_false_verified() {
            List<Lecture> lectures = new LinkedList<>();
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문ㅋ", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문2", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "F", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스및벤처", 3, "D", ""));

            verifier2.match(lectures);

            LectureSnapshot snapshot = (LectureSnapshot) verifier2.takeSnapshot();

            assertNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }

        @Test
        void snapshot_after_match_succeed_on_verifier1_has_not_null_matched_and_false_verified() {
            List<Lecture> lectures = new LinkedList<>();
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문2", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "F", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스및벤처", 3, "D", ""));

            verifier1.match(lectures);

            LectureSnapshot snapshot = (LectureSnapshot) verifier1.takeSnapshot();

            assertNotNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }
    }

    @Nested
    class after_verify {
        @Test
        void snapshot_after_verify_failed_on_verifier1_has_not_null_matched_and_false_verified() {
            List<Lecture> lectures = new LinkedList<>();
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문2", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "F", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스및벤처", 3, "D", ""));

            verifier1.match(lectures);
            verifier1.verify();

            LectureSnapshot snapshot = (LectureSnapshot) verifier1.takeSnapshot();

            assertNotNull(snapshot.matched);
            assertFalse(snapshot.verified);
        }

        @Test
        void snapshot_after_verify_succeed_on_verifier1_has_not_null_matched_and_false_verified() {
            List<Lecture> lectures = new LinkedList<>();
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문2", 3, "D", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스입문", 3, "F", ""));
            lectures.add(new Lecture("", "", "", "", "XR비즈니스및벤처", 3, "D", ""));

            verifier1.match(lectures);
            verifier1.hold();
            verifier1.verify();

            LectureSnapshot snapshot = (LectureSnapshot) verifier1.takeSnapshot();

            assertNotNull(snapshot.matched);
            assertTrue(snapshot.verified);
        }
    }
}