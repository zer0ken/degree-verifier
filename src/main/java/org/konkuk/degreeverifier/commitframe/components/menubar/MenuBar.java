package org.konkuk.degreeverifier.commitframe.components.menubar;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());
        add(new CommitMenu());
        add(new SettingMenu());

        add(Box.createHorizontalStrut(30));
        add(new QuickToolbar());
        add(Box.createGlue());
        add(new SettingToolbar());
        add(Box.createHorizontalStrut(10));
    }

    public JButton add(Action action) {
        JButton button = new JButton(action);
        button.setHideActionText(true);
        super.add(button);
        return button;
    }
}
