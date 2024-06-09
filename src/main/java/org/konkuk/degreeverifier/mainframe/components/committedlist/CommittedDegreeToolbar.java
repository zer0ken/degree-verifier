package org.konkuk.degreeverifier.mainframe.components.committedlist;

import org.konkuk.degreeverifier.common.components.InnerTitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.ClearCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.mainframe.actions.ExportCommitAction;

import static org.konkuk.degreeverifier.ui.Strings.COMMITTED_DEGREE_LIST;

public class CommittedDegreeToolbar extends InnerTitledToolbar {
    public CommittedDegreeToolbar() {
        super(COMMITTED_DEGREE_LIST);

        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new ExportCommitAction());
    }
}
