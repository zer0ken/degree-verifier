package org.konkuk.degreeverifier.actions;

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
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/verifier_folder_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                Runtime.getRuntime().exec("Explorer.exe " + DefaultPaths.VERIFIERS_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
