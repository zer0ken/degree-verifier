package org.konkuk.degreeverifier.common.components;

import org.konkuk.degreeverifier.mainframe.actions.informationtree.FoldAction;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.UnfoldAction;
import org.konkuk.degreeverifier.mainframe.components.informationtree.InformationTree;

import static org.konkuk.degreeverifier.ui.Strings.INFORMATION;

public class FoldableTreeToolbar extends TitledToolbar {
    public FoldableTreeToolbar(FoldableTree tree, String title) {
        super(title);

        add(new FoldAction(tree));
        add(new UnfoldAction(tree));
    }
}
