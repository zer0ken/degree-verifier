package org.konkuk.degreeverifier.common.components;

import org.konkuk.degreeverifier.common.actions.FoldAction;
import org.konkuk.degreeverifier.common.actions.UnfoldAction;

public class FoldableTreeToolbar extends TitledToolbar {
    public FoldableTreeToolbar(FoldableTree tree, String title) {
        super(title);

        add(new FoldAction(tree));
        add(new UnfoldAction(tree));
    }
}
