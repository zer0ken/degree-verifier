package org.konkuk.degreeverifier.logic.statusbar;

import org.konkuk.degreeverifier.components.statusbar.progress.ProgressStatusPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProgressStatusMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        ProgressStatusPanel panel = (ProgressStatusPanel) e.getSource();
        panel.getPopup().show(panel, e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
