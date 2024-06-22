package org.konkuk.degreeverifier.editorframe.components.verifiertree;

import org.konkuk.degreeverifier.common.components.FoldableTree;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeCellRenderer;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeSelectionListener;

public class VerifierTree extends FoldableTree {
    public VerifierTree() {
        setRootVisible(false);
        setModel(new VerifierTreeModel(this));
        setCellRenderer(new VerifierTreeCellRenderer());
        addTreeSelectionListener(new VerifierTreeSelectionListener());
    }
}
