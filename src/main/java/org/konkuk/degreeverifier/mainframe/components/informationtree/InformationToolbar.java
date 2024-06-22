package org.konkuk.degreeverifier.mainframe.components.informationtree;

import org.konkuk.degreeverifier.common.components.FoldableTreeToolbar;
import org.konkuk.degreeverifier.common.components.TitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.FoldAction;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.UnfoldAction;

import static org.konkuk.degreeverifier.ui.Strings.INFORMATION;

public class InformationToolbar extends FoldableTreeToolbar {
    public InformationToolbar(InformationTree tree) {
        super(tree, INFORMATION);
    }
}
