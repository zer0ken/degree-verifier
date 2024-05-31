package org.konkuk.client.components.menubar;

import org.konkuk.client.actions.LoadVerifierAction;
import org.konkuk.client.actions.OpenStudentDirectoryAction;
import org.konkuk.client.actions.LoadStudentListAction;

import javax.swing.*;

import static org.konkuk.client.ui.Strings.FILE_MENU;

public class FileMenu extends JMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new LoadStudentListAction());
        add(new LoadVerifierAction());
        addSeparator();
        add(new OpenStudentDirectoryAction());
    }
}
