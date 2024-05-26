package org.konkuk.client.component;

import org.konkuk.client.controller.VerifierPanelController;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.client.ui.Dimensions.VERIFIER_PANEL_SIZE;
import static org.konkuk.client.ui.Strings.VERIFIER_PANEL_TITLE;

public class VerifierPanel extends JPanel {
    private final JTree verifierTree;

    public VerifierPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(VERIFIER_PANEL_SIZE);

        verifierTree = new JTree();

        add(new TitlePanel(VERIFIER_PANEL_TITLE), BorderLayout.NORTH);
        add(verifierTree, BorderLayout.CENTER);
    }

    public JTree getVerifierTree() {
        return verifierTree;
    }
}
