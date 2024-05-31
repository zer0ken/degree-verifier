package org.konkuk.degreeverifier.logic.studentlist;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;

public class StudentListModel extends DefaultListModel<Student> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.ObserveOf.ON_STUDENT_LOADED, () -> {
            this.removeAllElements();
            appModel.getStudents().forEach(this::addElement);
        });
    }
}
