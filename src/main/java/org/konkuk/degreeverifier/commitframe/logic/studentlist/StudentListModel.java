package org.konkuk.degreeverifier.commitframe.logic.studentlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.util.Map;

public class StudentListModel extends DefaultListModel<Student> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, students -> {
            init((Map<String, Student>) students);
        });
        if(!appModel.isStudentMapEmpty()) {
            init(appModel.getStudents());
        }
        appModel.observe(AppModel.On.COMMIT_UPDATED, unused -> {
            init(appModel.getStudents());
        });
    }

    private void init(Map<String, Student> students) {
        this.removeAllElements();
        for (Student student : students.values()) {
            addElement(student);
        }
    }
}
