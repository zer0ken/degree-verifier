package org.konkuk.degreeverifier.mainframe.logic.lecturelist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.table.DefaultTableModel;

public class LectureTableModel extends DefaultTableModel {
    private final AppModel appModel = AppModel.getInstance();

    public LectureTableModel() {
        setColumnIdentifiers(LectureData.getColumns());
        appModel.observe(AppModel.On.LECTURE_UPDATED, this::_updateTable);
    }

    private void _updateTable(Object o) {
        Student student = (Student) o;
        setRowCount(0);
        if (student.isLoaded()) {
            updateTable(student);
        }
    }

    private void updateTable(Student student) {
        student.forEach(lecture -> addRow(lecture.inRow()));
    }
}
