package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.*;
import org.konkuk.degreeverifier.common.actions.OpenVerifierDirectoryAction;
import org.konkuk.degreeverifier.common.components.ActionMenu;

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
