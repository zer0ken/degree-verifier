package org.konkuk.degreeverifier.commitframe.logic.informationtree.nodes;

import javax.swing.tree.DefaultMutableTreeNode;

import static org.konkuk.degreeverifier.ui.Strings.USED_LECTURES;

public class LecturesRootNode extends DefaultMutableTreeNode {
    private final int count;

    public LecturesRootNode(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format(USED_LECTURES, count);
    }
}
