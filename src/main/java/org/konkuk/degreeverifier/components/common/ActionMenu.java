package org.konkuk.degreeverifier.components.common;

import javax.swing.*;

public class ActionMenu extends JMenu {
    public ActionMenu(String text) {
        super(text);
    }

    @Override
    public JMenuItem add(Action a) {
        JMenuItem menuItem = super.add(a);
        menuItem.setToolTipText(null);
        return menuItem;
    }
}
