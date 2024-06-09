package org.konkuk.degreeverifier.common.logic;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowPopupMenuOnClick implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent component = (JComponent) e.getSource();
        JPopupMenu popup = component.getComponentPopupMenu();
        popup.show(component, 0, 0);
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
