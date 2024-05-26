package org.konkuk.client.component.menus;

import org.konkuk.client.ui.Strings;

import javax.swing.*;

import static org.konkuk.client.ui.Strings.FILE_MENU;
import static org.konkuk.client.ui.Strings.IMPORT_LECTURES_MENUITEM;

public class FileMenu extends JMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new JMenuItem(IMPORT_LECTURES_MENUITEM));
    }
}
