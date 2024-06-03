package org.konkuk.degreeverifier.components.verifiedlist;

import org.konkuk.degreeverifier.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.actions.OpenVerifierDirectoryAction;

import javax.swing.*;

public class VerifiedDegreePopupMenu extends JPopupMenu {
    public VerifiedDegreePopupMenu() {
        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
        addSeparator();
        add(new OpenVerifierDirectoryAction());
    }
}
