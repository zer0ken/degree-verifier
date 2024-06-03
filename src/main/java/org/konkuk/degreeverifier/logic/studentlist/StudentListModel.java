package org.konkuk.degreeverifier.logic.studentlist;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.util.List;

public class StudentListModel extends DefaultListModel<Student> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.On.STUDENT_LOADED, students -> {
            this.removeAllElements();
            ((List<Student>) students).forEach(this::addElement);
        });
    }
}
