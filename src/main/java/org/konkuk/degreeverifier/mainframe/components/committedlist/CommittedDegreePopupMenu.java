package org.konkuk.degreeverifier.mainframe.components.committedlist;

import org.konkuk.degreeverifier.mainframe.actions.ClearCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.mainframe.actions.ExportCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.OpenExportDirectoryAction;

import javax.swing.*;

public class CommittedDegreePopupMenu extends JPopupMenu {
    public CommittedDegreePopupMenu() {
        add(new ExportCommitAction());
        addSeparator();
        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new OpenExportDirectoryAction());
    }
}
