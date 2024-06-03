package org.konkuk.degreeverifier.components.studentlist;

import org.konkuk.degreeverifier.actions.LoadStudentListAction;
import org.konkuk.degreeverifier.actions.OpenStudentDirectoryAction;
import org.konkuk.degreeverifier.actions.StartCommitAction;
import org.konkuk.degreeverifier.actions.VerifyStudentAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        add(new VerifyStudentAction());
        addSeparator();
        add(new LoadStudentListAction());
        add(new OpenStudentDirectoryAction());
    }
}
