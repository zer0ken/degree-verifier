package org.konkuk.degreeverifier.commitframe.components.studentlist;

import org.konkuk.degreeverifier.commitframe.actions.StartCommitAction;
import org.konkuk.degreeverifier.common.components.TitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.STUDENT_LIST;

public class StudentToolbar extends TitledToolbar {
    public StudentToolbar() {
        super(STUDENT_LIST);

        add(new StartCommitAction());
    }
}
