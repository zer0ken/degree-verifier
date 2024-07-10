package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_STUDENT;

public class VerifyStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public VerifyStudentAction() {
        putValue(NAME, VERIFY_STUDENT);
        putValue(SHORT_DESCRIPTION, VERIFY_STUDENT + " (Alt+V)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/sync_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK));

        setEnabled(!appModel.getSelectedStudents().isEmpty());

        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, students ->
                setEnabled(!appModel.getSelectedStudents().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.verifySelectedStudents();
    }
}
