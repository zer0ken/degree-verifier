package org.konkuk.degreeverifier.mainframe.components.informationtree;

import org.konkuk.degreeverifier.common.components.TitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.FoldAction;
import org.konkuk.degreeverifier.mainframe.actions.informationtree.UnfoldAction;

import static org.konkuk.degreeverifier.ui.Strings.INFORMATION;

public class InformationToolbar extends TitledToolbar {
    public InformationToolbar(InformationTree tree) {
        super(INFORMATION);

        add(new FoldAction(tree));
        add(new UnfoldAction(tree));
    }
}
