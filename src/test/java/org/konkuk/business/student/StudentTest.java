package org.konkuk.business.student;

//import com.sun.media.sound.InvalidFormatException;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.Verifier;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    final Student student = new Student(DefaultPaths.STUDENTS_PATH + "/201911205 - 이현령");
    private Verifier verifier;

    /*StudentTest() throws InvalidFormatException {
    }*/

    @BeforeEach
    void setUp() {

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(DefaultPaths.VERIFIERS_PATH + "/개정2차 실감미디어기술.json")) {
            DegreeVerifier degreeVerifier = gson.fromJson(reader, DegreeVerifier.class);
            verifier = new Verifier(new LinkedList<>(Arrays.asList(degreeVerifier)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void not_loaded_Student() {
        assertEquals("이현령", student.name);
        assertEquals("201911205", student.id);
        assertFalse(student.isLoaded());
        assertFalse(student.isVerified());
    }

    @Test
    void loaded_and_not_verified_Student() {
        student.loadLectures();
        assertTrue(student.isLoaded());
        assertEquals(18, student.size());
    }

    @Test
    void verify_and_check_results() {
        student.loadLectures();
        verifier.verify(student);

        assertTrue(student.isVerified());
        assertNotNull(student.getVerifiedSnapshotBundles());
        assertFalse(student.getVerifiedSnapshotBundles().isEmpty());
        assertNotNull(student.getSufficientDegrees());
        assertNotNull(student.getInsufficientDegrees());
        assertNotNull(student.getNotVerifiedDegrees());
    }

    @Test
    void check_student_lectures_loading() {
        student.loadLectures();
        assertTrue(student.isLoaded());
        assertFalse(student.isEmpty());
        student.forEach(lecture -> System.out.println(lecture));
    }
    @Test
    void check_verifier_data() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(DefaultPaths.VERIFIERS_PATH + "/개정2차 실감미디어기술.json")) {
            DegreeVerifier degreeVerifier = gson.fromJson(reader, DegreeVerifier.class);
            assertNotNull(degreeVerifier);
            System.out.println(degreeVerifier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void verify_and_debug() {
        student.loadLectures();
        verifier.verify(student);

        assertTrue(student.isVerified());

        if (student.getSufficientDegrees().isEmpty()) {
            System.out.println("Sufficient degrees is empty");
            student.getVerifiedSnapshotBundles().forEach(bundle -> {
                System.out.println(bundle);
            });
        }
    }
    @Test
    void commit_and_decommit_degrees() {
        student.loadLectures();
        verifier.verify(student);

        assertFalse(student.getSufficientDegrees().isEmpty(), "Sufficient degrees should not be empty");

        DegreeSnapshot degreeSnapshot = student.getSufficientDegrees().values().iterator().next();

        student.commitAll(Arrays.asList(degreeSnapshot));
        assertTrue(student.getCommittedDegrees().containsKey(degreeSnapshot.toString()));

        student.decommitAll(Arrays.asList(degreeSnapshot));
        assertFalse(student.getCommittedDegrees().containsKey(degreeSnapshot.toString()));
    }

    @Test
    void clear_commit() {
        student.loadLectures();
        verifier.verify(student);

        DegreeSnapshot degreeSnapshot = student.getSufficientDegrees().values().iterator().next();
        student.commitAll(Arrays.asList(degreeSnapshot));

        student.clearCommit();
        assertTrue(student.getCommittedDegrees().isEmpty());
    }
}