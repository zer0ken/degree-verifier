package org.konkuk.degreeverifier.editorframe.components.verifiertree;

import org.konkuk.degreeverifier.common.components.FoldableTree;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;

public class VerifierTree extends FoldableTree {
    public VerifierTree() {
        setRootVisible(false);
        setModel(new VerifierTreeModel(this));
    }
}
