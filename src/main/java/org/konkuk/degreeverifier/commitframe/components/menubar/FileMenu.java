package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.ExportCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadTranscriptAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadVerifierAction;
import org.konkuk.degreeverifier.common.components.ActionMenu;

import static org.konkuk.degreeverifier.ui.Strings.FILE_MENU;

public class FileMenu extends ActionMenu {
    public FileMenu() {
        super(FILE_MENU);

        add(new LoadVerifierAction());
        add(new LoadTranscriptAction());
        add(new LoadCommitAction());
        addSeparator();
        add(new ExportCommitAction());
    }
}
