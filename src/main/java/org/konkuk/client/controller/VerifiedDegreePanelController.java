package org.konkuk.client.controller;

import org.konkuk.client.component.VerifiedDegreePanel;
import org.konkuk.client.model.AppModel;
import org.konkuk.client.model.VerifiedDegreeTreeModel;

import javax.swing.*;

public class VerifiedDegreePanelController {
    private final AppModel appModel = AppModel.getInstance();
    private final JPanel panel;
    private final JTree tree;

    public VerifiedDegreePanelController(VerifiedDegreePanel panel) {
        this.panel = panel;
        panel.setVisible(false);

        appModel.observe(AppModel.Observer.ON_START_VERIFY, () -> panel.setVisible(true));

        tree = panel.getVerifiedDegreeTree();

        tree.setRootVisible(false);
        tree.setModel(new VerifiedDegreeTreeModel());
    }
}
