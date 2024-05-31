package org.konkuk.client.logic.studentlist;

import org.konkuk.client.AppModel;
import org.konkuk.common.student.Student;

import javax.swing.*;

public class StudentListEnable {
    public StudentListEnable(JList<Student> studentList) {
        AppModel.getInstance().observe(AppModel.ObserveOf.ON_STUDENT_LOAD_STARTED, () -> studentList.setEnabled(false));
        AppModel.getInstance().observe(AppModel.ObserveOf.ON_STUDENT_LOADED, () -> studentList.setEnabled(true));
    }
}
