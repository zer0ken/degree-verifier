package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.*;
import org.konkuk.degreeverifier.common.components.SizedToolbar;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_BUTTON_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_ICON_SCALE;

public class QuickToolbar extends SizedToolbar {
    public QuickToolbar() {
        super(MENU_TOOLBAR_BUTTON_SIZE, MENU_TOOLBAR_ICON_SCALE);
        add(new ExportCommitAction());
        add(new VerifyAllStudentAction());
        add(Box.createHorizontalStrut(20));
        add(new CommitAllStudentAction());
        add(Box.createHorizontalStrut(10));
        add(new StartCommitPreviousAction());
        add(new CommitRecommendedDegreesAction());
        add(new StartCommitNextAction());
    }
}
