package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.START_COMMIT;

public class StartCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitAction() {
        putValue(NAME, START_COMMIT);
        putValue(SHORT_DESCRIPTION, START_COMMIT + " (Alt+→)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/commit_student.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK));

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
