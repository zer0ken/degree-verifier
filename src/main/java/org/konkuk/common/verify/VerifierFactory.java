package org.konkuk.common.verify;

import org.konkuk.common.DefaultPaths;
import org.konkuk.common.FileUtil;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.student.Student;
import org.konkuk.common.verify.criteria.DegreeCriteria;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;
import org.konkuk.common.verify.verifier.DegreeVerifier;
import org.konkuk.common.verify.verifier.LectureVerifier;
import org.paukov.combinatorics3.Generator;

import java.io.File;
import java.util.*;

public class VerifierFactory extends LinkedList<DegreeVerifier> {
    private boolean isLoaded = false;

    synchronized public void loadAllVerifiers(ProgressTracker tracker) {
        File directory = new File(DefaultPaths.VERIFIERS_PATH);
        File[] specs = directory.listFiles();
        if (specs == null) {
            tracker.finish();
            return;
        }

        clear();
        tracker.setMaximum(specs.length);

        for (File spec : specs) {
            if (spec.isFile() && spec.getName().endsWith(".json")) {
                add(new DegreeVerifier(FileUtil.fromJsonFile(spec.getAbsolutePath(), DegreeCriteria.class)));
            }
            tracker.increment();
        }
        tracker.finish();
        isLoaded = true;
    }

    public Verifier newVerifier() {
        return new Verifier(this);
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
