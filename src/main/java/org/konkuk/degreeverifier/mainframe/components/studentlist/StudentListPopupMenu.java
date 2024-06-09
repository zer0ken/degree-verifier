package org.konkuk.degreeverifier.mainframe.components.studentlist;

import org.konkuk.degreeverifier.mainframe.actions.*;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        add(new VerifyStudentAction());
        addSeparator();
        add(new CreateStudentAction());
        add(new LoadStudentListAction());
        add(new OpenStudentDirectoryAction());
    }
}
