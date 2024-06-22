package org.konkuk.degreeverifier.editorframe.components.verifiertree;

import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_EVERY_SIDE;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_VERIFIER_TREE_SIZE;

public class VerifierTreePanel extends JPanel {
    public VerifierTreePanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_VERIFIER_TREE_SIZE);
        setBorder(SEPARATOR_EVERY_SIDE);

        VerifierTree tree = new VerifierTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new VerifierTreeToolbar(tree), BorderLayout.NORTH);
        add(scrollPane);
    }
}
