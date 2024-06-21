package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifierListSelectionList implements ListSelectionListener {
    private final EditorModel editorModel = EditorModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList<DegreeVerifier> list = (JList<DegreeVerifier>) e.getSource();
        editorModel.setSelectedVerifier(list.getSelectedValue());
    }
}
