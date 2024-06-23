package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;

import javax.swing.*;
import java.util.Collection;

public class VerifierListModel extends DefaultListModel<EditableDegreeCriteria> {
    private final EditorModel editorModel = EditorModel.getInstance();

    public VerifierListModel() {
        if (!editorModel.getDegrees().isEmpty()) {
            update(editorModel.getDegrees());
        }

        editorModel.observe(EditorModel.On.SAVED, unused -> update(editorModel.getDegrees()));
    }

    private void update(Collection<EditableDegreeCriteria> degrees) {
        clear();
        for (EditableDegreeCriteria degree : degrees) {
            addElement(degree);
        }
    }
}
