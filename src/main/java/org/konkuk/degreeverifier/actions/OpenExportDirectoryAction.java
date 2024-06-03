package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.konkuk.degreeverifier.ui.Strings.OPEN_EXPORT_DIRECTORY;

public class OpenExportDirectoryAction extends AbstractAction {
    public OpenExportDirectoryAction() {
        putValue(NAME, OPEN_EXPORT_DIRECTORY);
        putValue(SHORT_DESCRIPTION, OPEN_EXPORT_DIRECTORY);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/export_folder_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                Runtime.getRuntime().exec("Explorer.exe " + DefaultPaths.EXPORT_PATH);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
