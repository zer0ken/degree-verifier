package org.konkuk.client.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.common.DefaultPaths;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.konkuk.client.ui.Strings.OPEN_STUDENT_DIRECTORY;

public class OpenStudentDirectoryAction extends AbstractAction {
    public OpenStudentDirectoryAction() {
        putValue(NAME, OPEN_STUDENT_DIRECTORY);
        putValue(SHORT_DESCRIPTION, OPEN_STUDENT_DIRECTORY);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("org/konkuk/icons/student_folder_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                Runtime.getRuntime().exec("Explorer.exe " + DefaultPaths.STUDENTS_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
