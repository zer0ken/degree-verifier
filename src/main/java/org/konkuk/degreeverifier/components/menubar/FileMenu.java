package org.konkuk.degreeverifier.components.menubar;

import org.konkuk.degreeverifier.actions.*;
import org.konkuk.degreeverifier.components.common.ActionMenu;

import static org.konkuk.degreeverifier.ui.Strings.FILE_MENU;

public class FileMenu extends ActionMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new LoadStudentListAction());
        add(new LoadVerifierAction());
        addSeparator();
        add(new OpenStudentDirectoryAction());
        add(new OpenVerifierDirectoryAction());
        add(new OpenExportDirectoryAction());
    }
}
