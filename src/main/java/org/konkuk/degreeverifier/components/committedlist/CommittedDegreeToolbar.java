package org.konkuk.degreeverifier.components.committedlist;

import org.konkuk.degreeverifier.actions.ClearCommitAction;
import org.konkuk.degreeverifier.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.actions.ExportCommitAction;
import org.konkuk.degreeverifier.components.common.TitledToolbar2;

import static org.konkuk.degreeverifier.ui.Strings.COMMITTED_DEGREE_LIST;

public class CommittedDegreeToolbar extends TitledToolbar2 {
    public CommittedDegreeToolbar() {
        super(COMMITTED_DEGREE_LIST);

        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new ExportCommitAction());
    }
}
