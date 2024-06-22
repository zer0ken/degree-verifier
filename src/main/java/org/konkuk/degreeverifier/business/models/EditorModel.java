package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
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

    private final HashMap<String, DegreeCriteria> original = new HashMap<>();
    private final HashMap<String, DegreeCriteria> updated = new HashMap<>();
    private DegreeCriteria selectedCriteria = null;

    public void loadVerifiers() {
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            original.put(degreeVerifier.degreeName, degreeVerifier);
        }
    }

    public void saveChanges() {
        HashMap<String, DegreeCriteria> updated = new HashMap<>(this.updated);
        for (String key : updated.keySet()) {
            // try save, rollback on fail
            // also update original
        }
        this.updated.clear();
        notify(On.SAVED, updated);
    }

    public Collection<DegreeCriteria> getAllCriteria() {
        return original.values();
    }

    public void setSelectedCriteria(DegreeCriteria selectedCriteria) {
        this.selectedCriteria = selectedCriteria;
        notify(On.CRITERIA_SELECTED, selectedCriteria);
    }

    public DegreeCriteria getSelectedCriteria() {
        return selectedCriteria;
    }

    public enum On implements Event {
        CRITERIA_SELECTED,
        SAVED
    }
}
