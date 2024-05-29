package org.konkuk.client.component.menus;

import org.konkuk.client.component.menus.FileMenu;
import org.konkuk.client.component.menus.VerifyMenu;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        add(new FileMenu());
        add(new VerifyMenu());
    }
}
