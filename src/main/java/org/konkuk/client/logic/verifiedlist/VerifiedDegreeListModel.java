package org.konkuk.client.logic.verifiedlist;

import org.konkuk.client.AppModel;
import org.konkuk.common.student.Student;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import javax.swing.*;

public class VerifiedDegreeListModel extends DefaultListModel<DegreeSnapshot> {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListModel() {
        appModel.observe(AppModel.ObserveOf.ON_VERIFIED, this::_updateTree);
        appModel.observe(AppModel.ObserveOf.ON_COMMIT_STARTED, this::_updateTree);
        appModel.observe(AppModel.ObserveOf.ON_COMMIT_UPDATED, this::_updateTree);
    }

    private void _updateTree(Object o) {
        Student student = (Student) o;
        if (appModel.getCommittingStudent().equals(student)) {
            updateTree(student);
        }
    }

    public void updateTree(Student student) {
        removeAllElements();
        if (!student.isVerified()) {
            return;
        }
        student.getSufficientDegrees().values().forEach(this::addElement);
        if (!student.getInsufficientDegrees().isEmpty()) {
            addElement(null);
            student.getInsufficientDegrees().values().forEach(this::addElement);
        }
    }
}
