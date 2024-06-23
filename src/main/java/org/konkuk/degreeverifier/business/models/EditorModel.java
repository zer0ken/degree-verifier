package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.editable.Editable;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private final HashMap<String, EditableDegreeCriteria> degreeMap = new LinkedHashMap<>();
    private EditableDegreeCriteria selectedDegree = null;
    private final LinkedList<Editable> selectedNodes = new LinkedList<>();

    public void loadVerifiers() {
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            degreeMap.put(degreeVerifier.degreeName, new EditableDegreeCriteria(degreeVerifier));
        }
    }

    public void saveChanges() {
        for (String key : degreeMap.keySet()) {
            // TODO: try save, rollback on fail. also update original.
        }
        notify(On.SAVED, degreeMap);
    }

    public Collection<EditableDegreeCriteria> getDegrees() {
        return degreeMap.values();
    }

    public void setSelectedDegree(EditableDegreeCriteria selectedDegree) {
        this.selectedDegree = selectedDegree;
        notify(On.CRITERIA_SELECTED, selectedDegree);
    }

    public EditableDegreeCriteria getSelectedDegree() {
        return selectedDegree;
    }

    public void addSelectedNode(Editable selectedNode) {
        selectedNodes.add(selectedNode);
        notify(On.NODE_SELECTED, selectedNodes);
    }

    public void removeSelectedNode(Editable selectedNode) {
        selectedNodes.remove(selectedNode);
        notify(On.NODE_SELECTED, selectedNodes);
    }

    public LinkedList<Editable> getSelectedNodes() {
        return selectedNodes;
    }

    public enum On implements Event {
        CRITERIA_SELECTED,
        NODE_SELECTED,
        SAVED
    }
}
