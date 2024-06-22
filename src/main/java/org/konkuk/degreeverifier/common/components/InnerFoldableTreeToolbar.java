package org.konkuk.degreeverifier.common.components;

import org.konkuk.degreeverifier.mainframe.actions.informationtree.FoldAction;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.UnfoldAction;

public class InnerFoldableTreeToolbar extends InnerTitledToolbar {
    public InnerFoldableTreeToolbar(FoldableTree tree, String title) {
        super(title);

        add(new FoldAction(tree));
        add(new UnfoldAction(tree));
    }
}
