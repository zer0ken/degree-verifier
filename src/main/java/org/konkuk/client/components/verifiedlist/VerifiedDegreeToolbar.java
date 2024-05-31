package org.konkuk.client.components.verifiedlist;

import org.konkuk.client.actions.CommitDegreeAction;
import org.konkuk.client.actions.LoadStudentListAction;
import org.konkuk.client.actions.OpenStudentDirectoryAction;
import org.konkuk.client.actions.StartCommitAction;
import org.konkuk.client.components.TitledToolbar;

import static org.konkuk.client.ui.Strings.VERIFIED_DEGREE_LIST;

public class VerifiedDegreeToolbar extends TitledToolbar {
    public VerifiedDegreeToolbar() {
        super(VERIFIED_DEGREE_LIST, false);

        add(new CommitDegreeAction());
    }
}
