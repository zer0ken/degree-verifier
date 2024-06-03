package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.START_COMMIT;

public class StartCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitAction() {
        putValue(NAME, START_COMMIT);
        putValue(SHORT_DESCRIPTION, START_COMMIT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/commit_student.svg", getClass().getClassLoader()));

        setEnabled(!appModel.getSelectedStudents().isEmpty());

        appModel.observe(AppModel.On.STUDENT_SELECTED, students ->
                setEnabled(((List<Student>) students).size() == 1)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.startCommit();
    }
}
