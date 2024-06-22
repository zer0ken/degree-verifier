package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class EditorModel extends Observable {
    private static final EditorModel instance = new EditorModel();

    protected EditorModel() {
        if (appModel.isVerifierLoaded()) {
            loadVerifiers();
        }
    }

    public static EditorModel getInstance() {
        return instance;
    }

    private final AppModel appModel = AppModel.getInstance();

    private final HashMap<String, DegreeCriteria> criteriaMap = new HashMap<>();
    private DegreeCriteria selectedCriteria = null;
    private final LinkedList<RecursiveCriteria> selectedNodes = new LinkedList<>();

    public void loadVerifiers() {
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            criteriaMap.put(degreeVerifier.degreeName, degreeVerifier);
        }
    }

    public void saveChanges() {
        for (String key : criteriaMap.keySet()) {
            // TODO: try save, rollback on fail. also update original.
        }
        notify(On.SAVED, criteriaMap);
    }

    public Collection<DegreeCriteria> getAllCriteria() {
        return criteriaMap.values();
    }

    public void setSelectedCriteria(DegreeCriteria selectedCriteria) {
        this.selectedCriteria = updated.getOrDefault(selectedCriteria.degreeName, selectedCriteria);
        notify(On.CRITERIA_SELECTED, this.selectedCriteria);
    }

    public DegreeCriteria getSelectedCriteria() {
        return selectedCriteria;
    }

    public void addSelectedNode(RecursiveCriteria selectedNode) {
        selectedNodes.add(selectedNode);
        notify(On.NODE_SELECTED, selectedNodes);
    }

    public void removeSelectedNode(RecursiveCriteria selectedNode) {
        selectedNodes.remove(selectedNode);
        notify(On.NODE_SELECTED, selectedNodes);
    }

    public LinkedList<RecursiveCriteria> getSelectedNodes() {
        return selectedNodes;
    }

    public enum On implements Event {
        CRITERIA_SELECTED,
        NODE_SELECTED,
        SAVED
    }
}
