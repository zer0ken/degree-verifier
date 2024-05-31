package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_STUDENT;

public class VerifyStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public VerifyStudentAction() {
        putValue(NAME, VERIFY_STUDENT);
        putValue(SHORT_DESCRIPTION, VERIFY_STUDENT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/verify_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.getSelectedStudents().forEach(appModel::verifyStudent);
    }
}
