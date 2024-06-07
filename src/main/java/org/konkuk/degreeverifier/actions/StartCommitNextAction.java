package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.START_COMMIT_NEXT;

public class StartCommitNextAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitNextAction() {
        putValue(NAME, START_COMMIT_NEXT);
        putValue(SHORT_DESCRIPTION, START_COMMIT_NEXT + " (F4)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/skip_next_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));

        setEnabled(appModel.isStudentListLoaded() &&
                appModel.getCommittingStudentIndex() == appModel.getStudents().size() - 1);

        appModel.observe(AppModel.On.STUDENT_LOADED, students ->
                setEnabled(appModel.isStudentListLoaded() &&
                        appModel.getCommittingStudentIndex() < appModel.getStudents().size() - 1)
        );
        appModel.observe(AppModel.On.COMMIT_UPDATED, students ->
                setEnabled(appModel.isStudentListLoaded() &&
                        appModel.getCommittingStudentIndex() < appModel.getStudents().size() - 1)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (appModel.getCommittingStudent() == null) {
            appModel.startCommit(appModel.getStudents().get(0));
        }else {
            appModel.startCommitNext();
        }
    }
}
