package org.konkuk.degreeverifier.mainframe.logic.committedlist;

import org.konkuk.degreeverifier.mainframe.actions.DecommitDegreeAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommittedDegreeListMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
            new DecommitDegreeAction().actionPerformed(null);
        }
    }
}
