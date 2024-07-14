package org.konkuk.degreeverifier.commitframe.components.studentlist;

import org.konkuk.degreeverifier.commitframe.actions.LoadTranscriptAction;
import org.konkuk.degreeverifier.commitframe.actions.StartCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.VerifyStudentAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        add(new VerifyStudentAction());
        addSeparator();
        add(new LoadTranscriptAction());
    }
}
