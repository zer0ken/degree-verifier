package org.konkuk.degreeverifier.common.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.konkuk.degreeverifier.ui.Strings.OPEN_VERIFIER_DIRECTORY;

public class OpenVerifierDirectoryAction extends AbstractAction {
    public OpenVerifierDirectoryAction() {
        putValue(NAME, OPEN_VERIFIER_DIRECTORY);
        putValue(SHORT_DESCRIPTION, OPEN_VERIFIER_DIRECTORY);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/open_folder_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                Runtime.getRuntime().exec("Explorer.exe " + DefaultPaths.VERIFIERS_PATH);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
