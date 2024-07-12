package org.konkuk.degreeverifier.mainframe.components.studentlist;

import org.konkuk.degreeverifier.mainframe.actions.LoadTranscriptAction;
import org.konkuk.degreeverifier.mainframe.actions.StartCommitAction;
import org.konkuk.degreeverifier.mainframe.actions.VerifyStudentAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        add(new VerifyStudentAction());
        addSeparator();
        add(new LoadTranscriptAction());
    }
}
