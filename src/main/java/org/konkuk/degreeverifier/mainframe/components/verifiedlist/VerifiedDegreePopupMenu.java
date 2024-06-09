package org.konkuk.degreeverifier.mainframe.components.verifiedlist;

import org.konkuk.degreeverifier.mainframe.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.mainframe.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.mainframe.actions.OpenVerifierDirectoryAction;

import javax.swing.*;

public class VerifiedDegreePopupMenu extends JPopupMenu {
    public VerifiedDegreePopupMenu() {
        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
        addSeparator();
        add(new OpenVerifierDirectoryAction());
    }
}
