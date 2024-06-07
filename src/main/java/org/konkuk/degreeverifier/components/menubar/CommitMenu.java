package org.konkuk.degreeverifier.components.menubar;

import org.konkuk.degreeverifier.actions.*;
import org.konkuk.degreeverifier.components.common.ActionMenu;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_MENU;

public class CommitMenu extends ActionMenu {
    public CommitMenu() {
        super(COMMIT_MENU);
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
