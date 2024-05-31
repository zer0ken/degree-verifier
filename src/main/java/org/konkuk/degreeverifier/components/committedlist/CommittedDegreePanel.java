package org.konkuk.degreeverifier.components.committedlist;

import org.konkuk.degreeverifier.components.TitledToolbar;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_COMMITTED_DEGREE_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.COMMITTED_DEGREE_LIST;

public class CommittedDegreePanel extends JPanel {
    public CommittedDegreePanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_COMMITTED_DEGREE_SIZE);

        JTree tree = new JTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitledToolbar(COMMITTED_DEGREE_LIST), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
