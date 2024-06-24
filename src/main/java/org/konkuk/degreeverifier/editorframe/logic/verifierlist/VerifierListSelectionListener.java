package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifierListSelectionListener implements ListSelectionListener {
    private final EditorModel editorModel = EditorModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList<EditableDegreeCriteria> list = (JList<EditableDegreeCriteria>) e.getSource();
        editorModel.setSelectedDegrees(list.getSelectedValuesList());
    }
}
