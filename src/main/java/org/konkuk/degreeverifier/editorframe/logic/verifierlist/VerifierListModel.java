package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.*;
import java.util.Collection;

public class VerifierListModel extends DefaultListModel<DegreeVerifier> {
    private final EditorModel editorModel = EditorModel.getInstance();

    public VerifierListModel() {
        if (!editorModel.getVerifiers().isEmpty()) {
            update(editorModel.getVerifiers());
        }

        editorModel.observe(EditorModel.On.SAVED, unused -> update(editorModel.getVerifiers()));
    }

    private void update(Collection<DegreeVerifier> verifiers) {
        clear();
        for (DegreeVerifier degreeVerifier : verifiers) {
            addElement(degreeVerifier);
        }
    }
}
