package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.actions.OpenVerifierDirectoryAction;
import org.konkuk.degreeverifier.common.components.ActionMenu;
import org.konkuk.degreeverifier.mainframe.actions.*;

import static org.konkuk.degreeverifier.ui.Strings.FILE_MENU;

public class FileMenu extends ActionMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new LoadTranscriptAction());
        add(new LoadCommitAction());
        add(new LoadVerifierAction());
        addSeparator();
        add(new ExportCommitAction());
        addSeparator();
        add(new OpenVerifierDirectoryAction());
        add(new OpenCommitDirectoryAction());
    }
}
