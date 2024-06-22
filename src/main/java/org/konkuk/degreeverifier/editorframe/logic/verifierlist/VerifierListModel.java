package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;

import javax.swing.*;
import java.util.Collection;

public class VerifierListModel extends DefaultListModel<DegreeCriteria> {
    private final EditorModel editorModel = EditorModel.getInstance();

    public VerifierListModel() {
        if (!editorModel.getAllCriteria().isEmpty()) {
            update(editorModel.getAllCriteria());
        }

        editorModel.observe(EditorModel.On.SAVED, unused -> update(editorModel.getAllCriteria()));
    }

    private void update(Collection<DegreeCriteria> allCriteria) {
        clear();
        for (DegreeCriteria criteria : allCriteria) {
            addElement(criteria);
        }
    }
}
