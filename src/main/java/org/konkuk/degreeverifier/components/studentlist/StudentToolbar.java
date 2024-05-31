package org.konkuk.degreeverifier.components.studentlist;

import org.konkuk.degreeverifier.actions.StartCommitAction;
import org.konkuk.degreeverifier.actions.VerifyStudentAction;
import org.konkuk.degreeverifier.components.TitledToolbar;
import org.konkuk.degreeverifier.actions.LoadStudentListAction;

import static org.konkuk.degreeverifier.ui.Strings.STUDENT_LIST;

public class StudentToolbar extends TitledToolbar {
    public StudentToolbar() {
        super(STUDENT_LIST);

        add(new LoadStudentListAction());
        add(new VerifyStudentAction());
        add(new StartCommitAction());
    }
}
