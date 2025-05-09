package org.konkuk.degreeverifier.commitframe.components.committedlist;

import org.konkuk.degreeverifier.commitframe.actions.ClearCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.common.components.InnerTitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.COMMITTED_DEGREE_LIST;

public class CommittedDegreeToolbar extends InnerTitledToolbar {
    public CommittedDegreeToolbar() {
        super(COMMITTED_DEGREE_LIST);

        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
    }
}
