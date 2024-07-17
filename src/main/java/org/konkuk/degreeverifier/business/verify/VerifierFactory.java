package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.FileUtil;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_LOADING_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_SAVING_MESSAGE;

public class VerifierFactory extends LinkedList<DegreeVerifier> {
    private static final VerifierFactory instance = new VerifierFactory();

    protected VerifierFactory() {
    }

    public static VerifierFactory getInstance() {
        return instance;
    }

    private boolean isLoaded = false;

    private List<Set<String>> aliases = new LinkedList<>();

    synchronized public void loadVerifiers(File directory) {
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

    synchronized public void loadAliases(File file) {
        List<List<String>> table = FileUtil.fromCsvFile(file);
        for (List<String> row : table) {
            aliases.add(new HashSet<>(row.stream().map(String::toLowerCase).collect(Collectors.toSet())));
        }
    }

    synchronized public void updateAllVerifiers(Collection<DegreeCriteria> criteriaCollection) {
        clear();

        ProgressTracker tracker = new ProgressTracker(VERIFIER_SAVING_MESSAGE);
        tracker.setMaximum(criteriaCollection.size());
        for (DegreeCriteria degreeCriteria : criteriaCollection) {
            add(new DegreeVerifier(degreeCriteria));
        }
        String directory = DefaultPaths.VERIFIERS_PATH + "\\" + new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초\\").format(new Date());
        for (DegreeCriteria degreeCriteria : criteriaCollection) {
            FileUtil.toJsonFile(degreeCriteria, directory, degreeCriteria.toString(), DegreeCriteria.class);
            tracker.increment();
        }
        tracker.finish();
    }

    synchronized public Verifier createVerifier() {
        return new Verifier(this);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public List<Set<String>> getAliases() {
        return aliases;
    }
}
