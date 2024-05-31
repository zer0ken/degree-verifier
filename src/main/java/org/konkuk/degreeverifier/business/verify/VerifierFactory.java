package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.logic.ProgressTracker;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

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
