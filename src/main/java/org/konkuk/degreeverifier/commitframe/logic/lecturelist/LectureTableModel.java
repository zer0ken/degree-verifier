package org.konkuk.degreeverifier.commitframe.logic.lecturelist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.table.DefaultTableModel;

public class LectureTableModel extends DefaultTableModel {
    private final AppModel appModel = AppModel.getInstance();

    public LectureTableModel() {
        setColumnIdentifiers(LectureData.getColumns());
        appModel.observe(AppModel.On.LECTURE_UPDATED, o -> updateTable((Student) o));
    }

    private void updateTable(Student student) {
        setRowCount(0);
        student.forEach(lecture -> addRow(lecture.inRow()));
    }
}
