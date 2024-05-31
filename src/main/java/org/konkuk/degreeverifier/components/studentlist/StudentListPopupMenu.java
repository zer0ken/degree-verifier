package org.konkuk.degreeverifier.components.studentlist;

import org.konkuk.degreeverifier.actions.StartCommitAction;
import org.konkuk.degreeverifier.actions.VerifyStudentAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        add(new VerifyStudentAction());
    }
}
