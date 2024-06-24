package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.actions.OpenVerifierDirectoryAction;
import org.konkuk.degreeverifier.common.components.ActionMenu;
import org.konkuk.degreeverifier.mainframe.actions.LoadStudentListAction;
import org.konkuk.degreeverifier.mainframe.actions.LoadVerifierAction;
import org.konkuk.degreeverifier.mainframe.actions.OpenExportDirectoryAction;
import org.konkuk.degreeverifier.mainframe.actions.OpenStudentDirectoryAction;

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
