package org.konkuk.degreeverifier.mainframe.components.committedlist;

import org.konkuk.degreeverifier.mainframe.actions.ClearCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.mainframe.actions.OpenCommitDirectoryAction;

import javax.swing.*;

public class CommittedDegreePopupMenu extends JPopupMenu {
    public CommittedDegreePopupMenu() {
        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new OpenCommitDirectoryAction());
    }
}
