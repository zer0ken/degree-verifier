package org.konkuk.degreeverifier.editorframe.logic.verifierlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.VerifierFactory;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.*;

public class VerifierListModel extends DefaultListModel<DegreeVerifier> {
    private final AppModel appModel = AppModel.getInstance();
    public VerifierListModel() {
        if (appModel.getVerifierFactory().isLoaded()) {
            update(appModel.getVerifierFactory());
        }

        appModel.observe(AppModel.On.VERIFIER_LOADED, verifierFactory -> update((VerifierFactory) verifierFactory));
    }

    private void update(VerifierFactory verifierFactory) {
        clear();
        for (DegreeVerifier degreeVerifier : verifierFactory) {
            addElement(degreeVerifier);
        }
    }
}
