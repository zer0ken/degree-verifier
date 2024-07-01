package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.Editable;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;
import java.util.stream.Collectors;

public class EditorModel extends Observable {
    private static final EditorModel instance = new EditorModel();

    protected EditorModel() {
        if (appModel.isVerifierLoaded()) {
            loadVerifiers();
        }
        appModel.observe(AppModel.On.VERIFIER_LOADED, unused -> loadVerifiers());
    }

    public static EditorModel getInstance() {
        return instance;
    }

    private final AppModel appModel = AppModel.getInstance();

    private final Map<String, EditableDegreeCriteria> degreeMap = new TreeMap<>();
    private final LinkedList<EditableDegreeCriteria> selectedDegrees = new LinkedList<>();
    private final LinkedList<Editable> selectedNodeObjects = new LinkedList<>();
    private final LinkedList<DefaultMutableTreeNode> selectedNodes = new LinkedList<>();

    public void loadVerifiers() {
        degreeMap.clear();
        for (DegreeVerifier degreeVerifier : appModel.getVerifierFactory()) {
            degreeMap.put(degreeVerifier.toString(), new EditableDegreeCriteria(degreeVerifier));
        }
        notify(On.VERIFIER_LOADED, null);
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
        Map<String, EditableDegreeCriteria> changedDegreeMap = new LinkedHashMap<>();
        for (String key : degreeMap.keySet()) {
            DegreeCriteria changed = degreeMap.get(key).upcast();
            if (changed != null) {
                EditableDegreeCriteria changedEditable = new EditableDegreeCriteria(changed);
                changedDegreeMap.put(key, changedEditable);
            }
        }
        for (String key : changedDegreeMap.keySet()) {
            EditableDegreeCriteria changed = changedDegreeMap.get(key);
            degreeMap.remove(key);
            degreeMap.put(changed.toString(), changed);
        }
        notify(On.SAVED, degreeMap);
        appModel.updateVerifiers(degreeMap.values().stream()
                .map(c -> {
                    DegreeCriteria upcasted = c.upcast();
                    return upcasted != null ? upcasted : new DegreeCriteria(c.getOriginal());
                })
                .collect(Collectors.toList()));
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
        notify(On.DEGREE_UPDATED, getSelectedDegree());
    }

    public LinkedList<Editable> getSelectedNodeObjects() {
        return selectedNodeObjects;
    }

    public Editable getSelectedNodeObject() {
        return selectedNodeObjects.isEmpty() ? null : selectedNodeObjects.getFirst();
    }

    public List<EditableRecursiveCriteria> getRemovableSelectedNodeObjects() {
        return getSelectedNodeObjects().stream()
                .filter(c -> c instanceof EditableRecursiveCriteria
                        && !((EditableRecursiveCriteria) c).removed
                        && !((EditableRecursiveCriteria) c).isRoot)
                .map(c -> (EditableRecursiveCriteria) c)
                .collect(Collectors.toList());
    }

    public List<Editable> getRollbackableSelectedNodeObjects() {
        return getSelectedNodeObjects().stream()
                .filter(Editable::isEdited)
                .collect(Collectors.toList());
    }

    public boolean containsDegree(String degreeName) {
        return degreeMap.containsKey(degreeName);
    }

    public enum On implements Event {
        VERIFIER_LOADED,
        DEGREE_SELECTED,
        DEGREE_CREATED,
        DEGREE_REMOVED,
        DEGREE_UPDATED,
        NODES_SELECTED,
        SAVED
    }
}
