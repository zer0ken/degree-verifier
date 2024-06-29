package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.components.SizedToolbar;
import org.konkuk.degreeverifier.mainframe.actions.*;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_BUTTON_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_ICON_SCALE;

public class QuickToolbar extends SizedToolbar {
    public QuickToolbar() {
        super(MENU_TOOLBAR_BUTTON_SIZE, MENU_TOOLBAR_ICON_SCALE);
        add(new CommitAllStudentAction());
        add(Box.createHorizontalStrut(10));
        add(new StartCommitPreviousAction());
        add(new CommitRecommendedDegreesAction());
        add(new StartCommitNextAction());
        add(Box.createHorizontalStrut(10));
        add(new VerifyAllStudentAction());
    }
}
