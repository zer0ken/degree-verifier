package org.konkuk.degreeverifier.mainframe.components.informationtree;

import org.konkuk.degreeverifier.common.components.FoldableTree;
import org.konkuk.degreeverifier.mainframe.logic.informationtree.InformationTreeCellRenderer;
import org.konkuk.degreeverifier.mainframe.logic.informationtree.InformationTreeModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class InformationTree extends FoldableTree {
    public InformationTree() {
        super();
        setBackground(UIManager.getColor("RootPane.background"));
        setRootVisible(false);
        setModel(new InformationTreeModel(this));
        setCellRenderer(new InformationTreeCellRenderer());
    }
}
