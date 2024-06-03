package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class ClearCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ClearCommitAction() {
        putValue(NAME, CLEAR_COMMIT_DEGREE);
        putValue(SHORT_DESCRIPTION, CLEAR_COMMIT_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/remove_all_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (appModel.getCommittingStudent() == null || appModel.getCommittingStudent().getCommittedDegrees().isEmpty()) {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(null,
                CLEAR_COMMIT_DIALOG_MESSAGE, CLEAR_COMMIT_DIALOG_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            appModel.clearCommittedDegrees();
        }
    }
}
