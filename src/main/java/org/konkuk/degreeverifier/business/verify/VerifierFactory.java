package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.logic.statusbar.ProgressTracker;

import java.io.File;
import java.util.LinkedList;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_LOADING_MESSAGE;

public class VerifierFactory extends LinkedList<DegreeVerifier> {
    private boolean isLoaded = false;

    synchronized public void loadAllVerifiers() {
        File directory = new File(DefaultPaths.VERIFIERS_PATH);
        File[] specs = directory.listFiles();

        if (specs == null) {
            return;
        }
        clear();

        ProgressTracker tracker = new ProgressTracker(VERIFIER_LOADING_MESSAGE);
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

    synchronized public Verifier newVerifier() {
        return new Verifier(this);
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
