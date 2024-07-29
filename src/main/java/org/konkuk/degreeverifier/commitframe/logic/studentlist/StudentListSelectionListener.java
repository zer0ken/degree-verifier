package org.konkuk.degreeverifier.commitframe.logic.studentlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentListSelectionListener implements ListSelectionListener {
    private final AppModel appModel = AppModel.getInstance();
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList<Student> list = (JList) e.getSource();
        appModel.setSelectedStudents(list.getSelectedValuesList());
        list.ensureIndexIsVisible(list.getSelectedIndex());
    }
}
