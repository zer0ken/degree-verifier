package org.konkuk.degreeverifier.mainframe.logic.informationtree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import static org.konkuk.degreeverifier.ui.Strings.INSUFFICIENT_DEGREES;

public class InsufficientRootNode extends DefaultMutableTreeNode {
    @Override
    public String toString() {
        return INSUFFICIENT_DEGREES;
    }
}
