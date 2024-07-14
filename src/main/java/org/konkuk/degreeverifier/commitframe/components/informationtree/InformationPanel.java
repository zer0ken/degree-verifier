package org.konkuk.degreeverifier.commitframe.components.informationtree;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_INFORMATION_PANEL_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_INFORMATION_PANEL_SIZE;

public class InformationPanel extends JPanel {
    public InformationPanel() {
        setMinimumSize(MINIMUM_INFORMATION_PANEL_SIZE);
        setPreferredSize(PREFERRED_INFORMATION_PANEL_SIZE);
        setLayout(new BorderLayout());

        InformationTree tree = new InformationTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        add(scrollPane);
        add(new InformationToolbar(tree), BorderLayout.NORTH);
    }
}
