package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_ALL_STUDENT;

public class VerifyAllStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public VerifyAllStudentAction() {
        putValue(NAME, VERIFY_ALL_STUDENT);
        putValue(SHORT_DESCRIPTION, VERIFY_ALL_STUDENT + " (F5)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/sync_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

        setEnabled(!appModel.getStudents().isEmpty());

        appModel.observe(AppModel.On.STUDENT_LOADED, students ->
                setEnabled(!((Collection<Student>) students).isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.getStudents().forEach(appModel::verifyStudent);
    }
}
