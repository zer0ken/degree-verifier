package org.konkuk.client.component;

import org.konkuk.client.logic.VerifierTreeModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Strings.VERIFIER_LIST_PANEL_TITLE;

public class VerifierListPanel extends JPanel {
    public VerifierListPanel() {
        setLayout(new BorderLayout());

        JTree tree = new JTree();
        tree.setRootVisible(false);
        tree.setModel(new VerifierTreeModel());

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel(VERIFIER_LIST_PANEL_TITLE), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
