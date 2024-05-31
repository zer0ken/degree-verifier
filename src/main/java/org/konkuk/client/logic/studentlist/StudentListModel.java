package org.konkuk.client.logic.studentlist;

import org.konkuk.client.AppModel;
import org.konkuk.common.student.Student;

import javax.swing.*;

import static org.konkuk.client.ui.Strings.STUDENTS_LOADING_MESSAGE;

public class StudentListModel extends DefaultListModel<Student> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.ObserveOf.ON_STUDENT_LOADED, () -> {
            this.removeAllElements();
            appModel.getStudents().forEach(this::addElement);
        });
    }
}
