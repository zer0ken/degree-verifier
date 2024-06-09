package org.konkuk.degreeverifier.mainframe.logic.verifiedlist;

import org.konkuk.degreeverifier.mainframe.actions.CommitDegreeAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VerifiedDegreeListMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
            new CommitDegreeAction().actionPerformed(null);
        }
    }
}
