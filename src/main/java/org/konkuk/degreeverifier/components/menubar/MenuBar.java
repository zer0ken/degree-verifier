package org.konkuk.degreeverifier.components.menubar;

import org.konkuk.degreeverifier.components.menubar.studentnavigator.StudentNavigator;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());

        add(Box.createGlue());
        add(new StudentNavigator());
        add(Box.createGlue());
    }

    public JButton add(Action action) {
        JButton button = new JButton(action);
        button.setHideActionText(true);
        super.add(button);
        return button;
    }
}
