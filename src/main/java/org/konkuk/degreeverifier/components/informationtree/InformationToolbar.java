package org.konkuk.degreeverifier.components.informationtree;

import org.konkuk.degreeverifier.actions.informationtree.FoldAction;
import org.konkuk.degreeverifier.actions.informationtree.UnfoldAction;
import org.konkuk.degreeverifier.components.TitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.INFORMATION;

public class InformationToolbar extends TitledToolbar {
    public InformationToolbar(InformationTree tree) {
        super(INFORMATION);

        add(new FoldAction(tree));
        add(new UnfoldAction(tree));
    }
}
