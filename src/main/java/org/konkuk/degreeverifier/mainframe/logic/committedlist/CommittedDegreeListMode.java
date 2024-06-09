package org.konkuk.degreeverifier.mainframe.logic.committedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;

public class CommittedDegreeListMode extends DefaultListModel<DegreeSnapshot> {
    private final AppModel appModel = AppModel.getInstance();

    public CommittedDegreeListMode() {
        appModel.observe(AppModel.On.COMMIT_UPDATED, this::_updateTree);
    }

    private void _updateTree(Object o) {
        Student student = (Student) o;
        if (student.isVerified()) {
            updateTree(student);
        }
    }

    private void updateTree(Student student) {
        removeAllElements();
        student.getCommittedDegrees().values().forEach(this::addElement);
    }
}
