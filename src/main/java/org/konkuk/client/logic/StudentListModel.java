package org.konkuk.client.logic;

import org.konkuk.client.AppModel;

import javax.swing.*;

import static org.konkuk.client.ui.Strings.STUDENTS_LOADING_MESSAGE;

public class StudentListModel extends DefaultListModel<String> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.ObserveOf.ON_START_STUDENT_LOAD, () -> {
            clear();
            addElement(STUDENTS_LOADING_MESSAGE);
        });
        appModel.observe(AppModel.ObserveOf.ON_STUDENT_LOADED, () -> {
            clear();
            appModel.getStudents().forEach((student) -> addElement(student.toString()));
        });
    }
}
