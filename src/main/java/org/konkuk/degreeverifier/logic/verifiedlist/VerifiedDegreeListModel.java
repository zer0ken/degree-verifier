package org.konkuk.degreeverifier.logic.verifiedlist;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;

public class VerifiedDegreeListModel extends DefaultListModel<DegreeSnapshot> {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListModel() {
        appModel.observe(AppModel.ObserveOn.ON_COMMIT_UPDATED, this::_updateTree);
    }

    private void _updateTree(Object o) {
        Student student = (Student) o;
        if (student.isVerified()) {
            updateTree(student);
        }
    }

    private void updateTree(Student student) {
        removeAllElements();
        student.getSufficientDegrees().values().forEach(this::addElement);
        if (!student.getInsufficientDegrees().isEmpty()) {
            addElement(null);
            student.getInsufficientDegrees().values().forEach(this::addElement);
        }
    }
}
