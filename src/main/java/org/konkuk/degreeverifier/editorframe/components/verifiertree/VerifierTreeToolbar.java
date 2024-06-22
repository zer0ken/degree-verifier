package org.konkuk.degreeverifier.editorframe.components.verifiertree;

import org.konkuk.degreeverifier.common.components.InnerFoldableTreeToolbar;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_TREE;

public class VerifierTreeToolbar extends InnerFoldableTreeToolbar {
    public VerifierTreeToolbar(VerifierTree tree) {
        super(tree, VERIFIER_TREE);
    }
}
