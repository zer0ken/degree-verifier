package org.konkuk.client.components.menubar;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());
    }
}
