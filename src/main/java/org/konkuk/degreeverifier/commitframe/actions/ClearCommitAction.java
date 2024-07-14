package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class ClearCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ClearCommitAction() {
        putValue(NAME, CLEAR_COMMITTED_DEGREE);
        putValue(SHORT_DESCRIPTION, CLEAR_COMMITTED_DEGREE + " (Ctrl+Alt+Backspace)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/clear_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,
                InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));

        setEnabled(appModel.getCommittingStudent() != null && !appModel.getCommittingStudent().getCommittedDegrees().isEmpty());

        appModel.observe(AppModel.On.COMMIT_UPDATED, student ->
                setEnabled(!((Student) student).getCommittedDegrees().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String list = "<ul>";
        for (String s : appModel.getCommittingStudent().getCommittedDegrees().keySet()) {
            list += "<li>" + s + "</li>";
        }
        list += "</ul>";
        int confirm = JOptionPane.showConfirmDialog((Component) e.getSource(),
                String.format(CLEAR_COMMIT_DIALOG_MESSAGE, list), CLEAR_COMMIT_DIALOG_TITLE, JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            appModel.clearCommittedDegrees();
        }
    }
}
