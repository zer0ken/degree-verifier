package org.konkuk.degreeverifier.components.committedlist;

import org.konkuk.degreeverifier.actions.ClearCommitAction;
import org.konkuk.degreeverifier.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.actions.ExportCommitAction;
import org.konkuk.degreeverifier.components.TitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.COMMITTED_DEGREE_LIST;

public class CommittedDegreeToolbar extends TitledToolbar {
    public CommittedDegreeToolbar() {
        super(COMMITTED_DEGREE_LIST);

        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new ExportCommitAction());
    }
}
