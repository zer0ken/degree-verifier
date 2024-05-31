package org.konkuk.degreeverifier.components.menubar;

import org.konkuk.degreeverifier.actions.LoadVerifierAction;
import org.konkuk.degreeverifier.actions.OpenStudentDirectoryAction;
import org.konkuk.degreeverifier.actions.LoadStudentListAction;
import org.konkuk.degreeverifier.actions.OpenVerifierDirectoryAction;
import org.konkuk.degreeverifier.components.ActionMenu;

import static org.konkuk.degreeverifier.ui.Strings.FILE_MENU;

public class FileMenu extends ActionMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new LoadStudentListAction());
        add(new LoadVerifierAction());
        addSeparator();
        add(new OpenStudentDirectoryAction());
        add(new OpenVerifierDirectoryAction());
    }
}
