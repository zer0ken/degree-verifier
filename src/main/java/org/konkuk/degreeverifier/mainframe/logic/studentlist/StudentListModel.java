package org.konkuk.degreeverifier.mainframe.logic.studentlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.util.Map;

public class StudentListModel extends DefaultListModel<Student> {
    public StudentListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, students -> {
            this.removeAllElements();
            ((Map<String, Student>) students).values().forEach(this::addElement);
        });
    }
}
