package org.konkuk.degreeverifier.components.committedlist;

import org.konkuk.degreeverifier.actions.ClearCommitAction;
import org.konkuk.degreeverifier.actions.DecommitDegreeAction;
import org.konkuk.degreeverifier.actions.ExportCommitAction;
import org.konkuk.degreeverifier.actions.OpenExportDirectoryAction;

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
