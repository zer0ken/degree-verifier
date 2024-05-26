package org.konkuk.client.controller;

import org.konkuk.client.component.VerifierPanel;
import org.konkuk.client.model.AppModel;
import org.konkuk.client.model.VerifiedDegreeTreeModel;
import org.konkuk.client.model.VerifierTreeModel;
import org.konkuk.common.DegreeManager;

import javax.swing.*;

public class VerifierPanelController {
    private final AppModel appModel = AppModel.getInstance();
    private final JPanel panel;
    private final JTree tree;

    public VerifierPanelController(VerifierPanel panel) {
        this.panel = panel;

        tree = panel.getVerifierTree();

        tree.setRootVisible(false);
        tree.setModel(new VerifierTreeModel());
    }
}
