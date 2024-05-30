package org.konkuk.client.component;

import org.konkuk.client.logic.VerifiedDegreeTreeModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Strings.VERIFIED_DEGREE_PANEL_TITLE;

public class VerifiedDegreePanel extends JPanel {
    public VerifiedDegreePanel() {
        setLayout(new BorderLayout());

        JTree tree = new JTree();
        tree.setRootVisible(false);
        tree.setModel(new VerifiedDegreeTreeModel());

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel(VERIFIED_DEGREE_PANEL_TITLE), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
