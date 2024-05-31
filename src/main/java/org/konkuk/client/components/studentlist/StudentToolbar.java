package org.konkuk.client.components.studentlist;

import org.konkuk.client.actions.StartCommitAction;
import org.konkuk.client.components.TitledToolbar;
import org.konkuk.client.actions.OpenStudentDirectoryAction;
import org.konkuk.client.actions.LoadStudentListAction;

import static org.konkuk.client.ui.Strings.STUDENT_LIST;

public class StudentToolbar extends TitledToolbar {
    public StudentToolbar() {
        super(STUDENT_LIST);

        add(new LoadStudentListAction());
        add(new OpenStudentDirectoryAction());
        add(new StartCommitAction());
    }
}
