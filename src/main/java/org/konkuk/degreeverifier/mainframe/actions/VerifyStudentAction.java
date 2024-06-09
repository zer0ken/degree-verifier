package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_STUDENT;

public class VerifyStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public VerifyStudentAction() {
        putValue(NAME, VERIFY_STUDENT);
        putValue(SHORT_DESCRIPTION, VERIFY_STUDENT + " (Alt+V)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/rule_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK));

        setEnabled(!appModel.getStudents().isEmpty());

        appModel.observe(AppModel.On.STUDENT_LOADED, students ->
                setEnabled(!((Collection<Student>) students).isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.getSelectedStudents().forEach(appModel::verifyStudent);
    }
}
