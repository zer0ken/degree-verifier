package org.konkuk.client.components.studentlist;

import org.konkuk.client.actions.StartCommitAction;

import javax.swing.*;

public class StudentListPopupMenu extends JPopupMenu {
    public StudentListPopupMenu() {
        add(new StartCommitAction());
    }
}
