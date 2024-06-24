package org.konkuk.degreeverifier.editorframe.components.verifiertree;

import org.konkuk.degreeverifier.common.components.InnerFoldableTreeToolbar;
import org.konkuk.degreeverifier.editorframe.actions.CreateRecursiveVerifierAction;
import org.konkuk.degreeverifier.editorframe.actions.OpenVerifierFileAction;
import org.konkuk.degreeverifier.editorframe.actions.RemoveRecursiveVerifierAction;
import org.konkuk.degreeverifier.editorframe.actions.RollbackSelectedNodeAction;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_TREE;

public class VerifierTreeToolbar extends InnerFoldableTreeToolbar {
    public VerifierTreeToolbar(VerifierTree tree) {
        super(tree, VERIFIER_TREE);
        addSeparator();
        add(new CreateRecursiveVerifierAction());
        add(new RemoveRecursiveVerifierAction());
        add(new RollbackSelectedNodeAction());
        addSeparator();
        add(new OpenVerifierFileAction());
    }
}
