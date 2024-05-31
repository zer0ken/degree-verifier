package org.konkuk.client.components.verifiedlist;

import org.konkuk.client.actions.CommitDegreeAction;

import javax.swing.*;

public class VerifiedDegreePopupMenu extends JPopupMenu {
    public VerifiedDegreePopupMenu() {
        add(new CommitDegreeAction());
    }
}
