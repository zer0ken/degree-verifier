package org.konkuk.client.component;

import org.konkuk.client.controller.VerifiedDegreePanelController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Dimensions.VERIFIED_DEGREE_PANEL_SIZE;
import static org.konkuk.client.ui.Strings.VERIFIED_DEGREE_PANEL_TITLE;

public class VerifiedDegreePanel extends JPanel {
    private final JTree verifiedDegreeTree;

    public VerifiedDegreePanel() {
        setLayout(new BorderLayout());
        setPreferredSize(VERIFIED_DEGREE_PANEL_SIZE);

        verifiedDegreeTree = new JTree();

        JPanel innerScrollPane = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(
                innerScrollPane,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        innerScrollPane.add(verifiedDegreeTree);

        add(new TitlePanel(VERIFIED_DEGREE_PANEL_TITLE), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        new VerifiedDegreePanelController(this);
    }

    public JTree getVerifiedDegreeTree() {
        return verifiedDegreeTree;
    }
}
