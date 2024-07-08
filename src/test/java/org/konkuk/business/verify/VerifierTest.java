package org.konkuk.business.verify;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.Verifier;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifierTest {

    private Verifier verifier;
    private final Student student = new Student(DefaultPaths.STUDENTS_PATH + "/201911205 - 이현령");

    @BeforeEach
    public void setUp() {
        LinkedList<DegreeVerifier> verifiers = new LinkedList<>();
        File verifiersDir = new File(DefaultPaths.VERIFIERS_PATH);
        Gson gson = new Gson();

        if (verifiersDir.exists() && verifiersDir.isDirectory()) {
            File[] verifierFiles = verifiersDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

            if (verifierFiles != null) {
                for (File file : verifierFiles) {
                    try (FileReader reader = new FileReader(file)) {
                        DegreeCriteria degreeCriteria = gson.fromJson(reader, DegreeCriteria.class);
                        DegreeVerifier verifier = new DegreeVerifier(degreeCriteria);
                        verifiers.add(verifier);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        verifier = new Verifier(verifiers);
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
        } else {
            System.out.println("Sufficient degrees:");
            student.getSufficientDegrees().forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });
        }
    }
}
