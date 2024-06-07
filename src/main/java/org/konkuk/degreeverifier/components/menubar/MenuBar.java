package org.konkuk.degreeverifier.components.menubar;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());
        add(new CommitMenu());

        add(Box.createHorizontalStrut(30));
        add(new QuickToolbar());
    }

    public JButton add(Action action) {
        JButton button = new JButton(action);
        button.setHideActionText(true);
        super.add(button);
        return button;
    }
}
