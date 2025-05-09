package org.konkuk.degreeverifier.commitframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items.*;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

import javax.swing.*;

public class VerifiedDegreeListModel extends DefaultListModel<VerifierListItem> {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListModel() {
        appModel.observe(AppModel.On.SELECTED_STUDENT_COMMIT_UPDATED, o -> updateTree((Student) o));
    }

    private void updateTree(Student student) {
        removeAllElements();
        if (student == null || !student.isVerified()) {
            return;
        }
        if (!student.getSufficientDegrees().isEmpty()) {
            addElement(new SufficientSeparatorItem());
        }
        student.getSufficientDegrees().values()
                .forEach(element -> addElement(new SufficientItem(element)));
        if (!student.getInsufficientDegrees().isEmpty()) {
            addElement(new InsufficientSeparatorItem());
            student.getInsufficientDegrees().values()
                    .forEach(element -> addElement(new InsufficientItem(element)));
        }
        if (!student.getNotVerifiedDegrees().isEmpty()) {
            addElement(new NotVerifiedSeparatorItem());
            student.getNotVerifiedDegrees().values()
                    .forEach(element -> addElement(new NotVerifiedItem(element)));
        }
    }
}
