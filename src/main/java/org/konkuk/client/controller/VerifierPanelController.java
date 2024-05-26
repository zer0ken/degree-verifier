package org.konkuk.client.controller;

import org.konkuk.client.component.VerifierPanel;
import org.konkuk.client.model.AppModel;
import org.konkuk.common.DegreeManager;

import javax.swing.*;

public class VerifierPanelController {
    private final AppModel appModel = AppModel.getInstance();

    public VerifierPanelController(VerifierPanel panel) {
        JTree tree = panel.getVerifierTree();
        DegreeManager degreeManager = appModel.getDegreeManager();

    }
}
