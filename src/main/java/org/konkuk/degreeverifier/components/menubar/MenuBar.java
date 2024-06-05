package org.konkuk.degreeverifier.components.menubar;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());

        add(Box.createGlue());
        add(new JButton("test"));
        add(new JButton("test"));
        add(new JButton("test"));
        add(Box.createGlue());
    }

    public JButton add(Action action) {
        JButton button = new JButton(action);
        button.setHideActionText(true);
        super.add(button);
        return button;
    }
}
