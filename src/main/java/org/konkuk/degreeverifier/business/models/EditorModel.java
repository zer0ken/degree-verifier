package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.editable.Editable;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

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
    private LinkedList<EditableDegreeCriteria> selectedDegrees = new LinkedList<>();
    private final LinkedList<Editable> selectedNodeObjects = new LinkedList<>();
    private final LinkedList<DefaultMutableTreeNode> selectedNodes = new LinkedList<>();

    public void loadVerifiers() {
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            degreeMap.put(degreeVerifier.degreeName, new EditableDegreeCriteria(degreeVerifier));
        }
    }

    public void createDegree() {
        String degreeName;
        do {
            degreeName = "새 학위 - " +
                    (new Random()).ints(97, 123)
                            .limit(10)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();
        } while (degreeMap.containsKey(degreeName));
        EditableDegreeCriteria degreeCriteria = new EditableDegreeCriteria(degreeName);
        degreeMap.put(degreeName, degreeCriteria);
        notify(On.DEGREE_CREATED, degreeCriteria);
    }

    public void removeSelectedDegree() {
        LinkedList<EditableDegreeCriteria> selectedDegrees = new LinkedList<>(this.selectedDegrees);
        setSelectedDegrees(null);

        for (EditableDegreeCriteria degree : selectedDegrees) {
            degreeMap.remove(degree.degreeName);
        }
        notify(On.DEGREE_REMOVED, selectedDegrees);
    }

    public void saveChanges() {
        for (String key : degreeMap.keySet()) {
            // TODO: 2024-06-25 implement this
        }
        notify(On.SAVED, degreeMap);
    }

    public Collection<EditableDegreeCriteria> getDegrees() {
        return degreeMap.values();
    }

    public void setSelectedDegrees(Collection<EditableDegreeCriteria> selectedDegrees) {
        this.selectedDegrees.clear();
        if (selectedDegrees != null) {
            this.selectedDegrees.addAll(selectedDegrees);
        }
        notify(On.DEGREE_SELECTED, this.selectedDegrees);
    }

    public LinkedList<EditableDegreeCriteria> getSelectedDegrees() {
        return selectedDegrees;
    }

    public EditableDegreeCriteria getSelectedDegree() {
        return selectedDegrees.isEmpty() ? null : selectedDegrees.getFirst();
    }

    public void addSelectedNode(DefaultMutableTreeNode selectedNode, Editable selectedNodeObject) {
        selectedNodes.add(selectedNode);
        selectedNodeObjects.add(selectedNodeObject);
        notify(On.NODES_SELECTED, selectedNodeObjects);
    }

    public void removeSelectedNode(DefaultMutableTreeNode selectedNode, Editable selectedNodeObject) {
        selectedNodes.add(selectedNode);
        selectedNodeObjects.remove(selectedNodeObject);
        notify(On.NODES_SELECTED, selectedNodeObjects);
    }

    public void notifyUpdatedSelectedDegree() {
        notify(On.DEGREE_UPDATED, null);
    }

    public LinkedList<Editable> getSelectedNodeObjects() {
        return selectedNodeObjects;
    }

    public Editable getSelectedNodeObject() {
        return selectedNodeObjects.isEmpty() ? null : selectedNodeObjects.getFirst();
    }

    public enum On implements Event {
        DEGREE_SELECTED,
        DEGREE_CREATED,
        DEGREE_REMOVED,
        DEGREE_UPDATED,
        NODES_SELECTED,
        SAVED
    }
}
