package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;

import javax.swing.*;
import java.util.Collection;
import java.util.LinkedList;

public class VerifierListModel extends DefaultListModel<EditableDegreeCriteria> {
    private final EditorModel editorModel = EditorModel.getInstance();

    public VerifierListModel() {
        if (!editorModel.getDegrees().isEmpty()) {
            update(editorModel.getDegrees());
        }

        editorModel.observe(EditorModel.On.SAVED, unused -> update(editorModel.getDegrees()));
        editorModel.observe(EditorModel.On.DEGREE_CREATED, o ->
                this.create((EditableDegreeCriteria) o));
        editorModel.observe(EditorModel.On.DEGREE_REMOVED, o ->
                this.removeSelected((LinkedList<EditableDegreeCriteria>) o));
    }

    private void update(Collection<EditableDegreeCriteria> degrees) {
        clear();
        for (EditableDegreeCriteria degree : degrees) {
            addElement(degree);
        }
    }

    private void create(EditableDegreeCriteria degreeCriteria) {
        insertElementAt(degreeCriteria, 0);
    }

    private void removeSelected(LinkedList<EditableDegreeCriteria> degreeCriteria) {
        for (EditableDegreeCriteria degree : degreeCriteria) {
            removeElement(degree);
        }
    }
}
