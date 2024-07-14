package org.konkuk.degreeverifier.commitframe.components.committedlist;

import org.konkuk.degreeverifier.commitframe.actions.ClearCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.commitframe.actions.OpenCommitDirectoryAction;

import javax.swing.*;

public class CommittedDegreePopupMenu extends JPopupMenu {
    public CommittedDegreePopupMenu() {
        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new OpenCommitDirectoryAction());
    }
}
