package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.util.Collection;
import java.util.HashMap;

public class EditorModel extends Observable {
    private static EditorModel instance = new EditorModel();

    protected EditorModel() {
        if (appModel.isVerifierLoaded()) {
            loadVerifiers();
        }
    }

    public static EditorModel getInstance() {
        return instance;
    }

    private final AppModel appModel = AppModel.getInstance();

    private DegreeVerifier selectedVerifier = null;

    private final HashMap<String, DegreeVerifier> original = new HashMap<>();
    private final HashMap<String, DegreeVerifier> updated = new HashMap<>();

    public void loadVerifiers() {
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            original.put(degreeVerifier.degreeName, degreeVerifier);
        }
    }

    public void saveChanges() {
        HashMap<String, DegreeVerifier> updated = new HashMap<>(this.updated);
        for (String key : updated.keySet()) {
            // try save, rollback on fail
            // also update original
        }
        this.updated.clear();
        notify(On.SAVED, updated);
    }

    public Collection<DegreeVerifier> getVerifiers() {
        return original.values();
    }

    public void setSelectedVerifier(DegreeVerifier selectedVerifier) {
        this.selectedVerifier = selectedVerifier;
        notify(On.VERIFIER_SELECTED, selectedVerifier);
    }

    public DegreeVerifier getSelectedVerifier() {
        return selectedVerifier;
    }

    public enum On implements Event {
        VERIFIER_SELECTED,
        SAVED
    }
}
