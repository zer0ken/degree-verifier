package org.konkuk.degreeverifier.mainframe.components.studentlist;

import org.konkuk.degreeverifier.common.components.TitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.StartCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.VerifyStudentAction;

import static org.konkuk.degreeverifier.ui.Strings.STUDENT_LIST;

public class StudentToolbar extends TitledToolbar {
    public StudentToolbar() {
        super(STUDENT_LIST);

        add(new VerifyStudentAction());
        add(new StartCommitAction());
    }
}
