package org.konkuk.degreeverifier.commitframe.components.verifiedlist;

import org.konkuk.degreeverifier.commitframe.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.commitframe.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.common.actions.OpenVerifierDirectoryAction;

import javax.swing.*;

public class VerifiedDegreePopupMenu extends JPopupMenu {
    public VerifiedDegreePopupMenu() {
        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
        addSeparator();
        add(new OpenVerifierDirectoryAction());
    }
}
