package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.konkuk.degreeverifier.ui.Strings.OPEN_LECTURE_DIRECTORY;

public class OpenLectureDirectoryAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public OpenLectureDirectoryAction() {
        putValue(NAME, OPEN_LECTURE_DIRECTORY);
        putValue(SHORT_DESCRIPTION, OPEN_LECTURE_DIRECTORY);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/student_folder_icon.svg", getClass().getClassLoader()));

        setEnabled(appModel.getCommittingStudent() != null && appModel.getCommittingStudent().isLoaded());
        appModel.observe(AppModel.On.COMMIT_STARTED, student -> setEnabled(((Student)student).isLoaded()));
        appModel.observe(AppModel.On.COMMIT_UPDATED, student -> setEnabled(((Student)student).isLoaded()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Student committingStudent = appModel.getCommittingStudent();
        if (committingStudent == null) {
            return;
        }
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                Runtime.getRuntime().exec("Explorer.exe " + committingStudent.getDirectoryName());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
