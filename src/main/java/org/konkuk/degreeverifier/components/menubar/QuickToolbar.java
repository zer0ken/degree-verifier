package org.konkuk.degreeverifier.components.menubar;

import org.konkuk.degreeverifier.actions.CommitAllStudentAction;
import org.konkuk.degreeverifier.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.actions.StartCommitNextAction;
import org.konkuk.degreeverifier.actions.StartCommitPreviousAction;
import org.konkuk.degreeverifier.components.common.SizedToolbar;

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
