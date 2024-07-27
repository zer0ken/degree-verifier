package org.konkuk.degreeverifier.commitframe.logic.committedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.EarlyCommittedItem;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.EarlyCommittedSeparatorItem;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.NewlyCommittedItem;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.NewlyCommittedSeparatorItem;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

import javax.swing.*;

public class CommittedDegreeListModel extends DefaultListModel<VerifierListItem> {
    private final AppModel appModel = AppModel.getInstance();

    public CommittedDegreeListModel() {
        appModel.observe(AppModel.On.SELECTED_STUDENT_COMMIT_UPDATED, o -> update((Student) o));
    }

    private void update(Student student) {
        removeAllElements();
        if (student == null) {
            return;
        }
        if (!student.getCommittedDegrees().isEmpty()) {
            addElement(new NewlyCommittedSeparatorItem());
            student.getCommittedDegrees().values().forEach(degree -> addElement(new NewlyCommittedItem(degree)));
        }
        if (!student.getEarlyCommittedDegrees().isEmpty()) {
            addElement(new EarlyCommittedSeparatorItem());
            student.getEarlyCommittedDegrees().values().forEach(degree -> addElement(new EarlyCommittedItem(degree)));
        }
    }
}
