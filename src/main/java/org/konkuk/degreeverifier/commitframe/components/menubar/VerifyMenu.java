package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.*;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_MENU;

public class VerifyMenu extends JMenu {
    public VerifyMenu() {
        super(VERIFY_MENU);

        add(new VerifyAllStudentAction());
        addSeparator();
        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
        add(new CommitAllStudentAction());
        addSeparator();
        add(new DecommitDegreeAction());
        add(new ClearCommitAction());
        addSeparator();
        add(new StartCommitAction());
        add(new StartCommitPreviousAction());
        add(new StartCommitNextAction());
    }
}
