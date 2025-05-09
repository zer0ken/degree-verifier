package org.konkuk.degreeverifier.common.components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class FoldableTree extends JTree {
    private int unfoldDepth = 2;

    public void fold() {
        if (unfoldDepth > 0) {
            unfoldDepth--;
        }
        updateFoldingState();
    }

    public void unfold() {
        if (unfoldDepth < ((DefaultMutableTreeNode) getModel().getRoot()).getDepth() - 1) {
            unfoldDepth++;
        }
        updateFoldingState();
    }

    public void setUnfoldDepth(int unfoldDepth) {
        this.unfoldDepth = unfoldDepth;
    }

    public void updateFoldingState() {
        for (int i = 0; i < getRowCount(); i++) {
            TreePath path = getPathForRow(i);
            if (path.getPathCount() - 1 <= unfoldDepth) {
                expandPath(path);
            } else {
                collapsePath(path);
            }
        }
    }
}
