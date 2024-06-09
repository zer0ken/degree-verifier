package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.components.SizedToolbar;
import org.konkuk.degreeverifier.mainframe.actions.CommitAllStudentAction;
import org.konkuk.degreeverifier.mainframe.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.mainframe.actions.StartCommitNextAction;
import org.konkuk.degreeverifier.mainframe.actions.StartCommitPreviousAction;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.QUICK_TOOLBAR_BUTTON_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.QUICK_TOOLBAR_ICON_SCALE;

public class QuickToolbar extends SizedToolbar {
    public QuickToolbar() {
        super(QUICK_TOOLBAR_BUTTON_SIZE, QUICK_TOOLBAR_ICON_SCALE);
        add(new CommitAllStudentAction());
        add(Box.createHorizontalStrut(10));
        add(new StartCommitPreviousAction());
        add(new CommitRecommendedDegreesAction());
        add(new StartCommitNextAction());
    }
}
