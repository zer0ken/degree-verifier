package org.konkuk.degreeverifier.mainframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.*;

import javax.swing.*;

public class VerifiedDegreeListModel extends DefaultListModel<VerifiedDegreeListItem> {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListModel() {
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
        if (!student.getSufficientDegrees().isEmpty()){
            addElement(new VerifiedDegreeListSufficientSeparatorItem());
        }
        student.getSufficientDegrees().values()
                .forEach(element -> addElement(new VerifiedDegreeListSufficientItem(element)));
        if (!student.getInsufficientDegrees().isEmpty()) {
            addElement(new VerifiedDegreeListInsufficientSeparatorItem());
            student.getInsufficientDegrees().values()
                    .forEach(element -> addElement(new VerifiedDegreeListInsufficientItem(element)));
        }
    }
}
