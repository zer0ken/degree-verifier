package org.konkuk.degreeverifier.commitframe.logic.studentlist;

import org.konkuk.degreeverifier.commitframe.actions.StartCommitAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentListMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
            new StartCommitAction().actionPerformed(null);
        }
    }
}
