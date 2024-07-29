package org.konkuk.degreeverifier.commitframe.components.studentlist;

import org.konkuk.degreeverifier.commitframe.actions.LoadTranscriptAction;
import org.konkuk.degreeverifier.commitframe.actions.StartCommitAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
        addSeparator();
        add(new LoadTranscriptAction());
    }
}
