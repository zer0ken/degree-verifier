package org.konkuk.degreeverifier.commitframe.components.informationtree;

import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTreeCellRenderer;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTreeModel;
import org.konkuk.degreeverifier.common.components.FoldableTree;

import javax.swing.*;

public class InformationTree extends FoldableTree {
    public InformationTree() {
        super();
        setBackground(UIManager.getColor("RootPane.background"));
        setRootVisible(false);
        setModel(new InformationTreeModel(this));
        setCellRenderer(new InformationTreeCellRenderer());
    }
}
