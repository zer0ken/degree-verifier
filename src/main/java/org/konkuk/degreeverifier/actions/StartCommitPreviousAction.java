package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.START_COMMIT_PREVIOUS;

public class StartCommitPreviousAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitPreviousAction() {
        putValue(NAME, START_COMMIT_PREVIOUS);
        putValue(SHORT_DESCRIPTION, START_COMMIT_PREVIOUS + " (F2)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/skip_previous_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

        setEnabled(appModel.isStudentListLoaded() && appModel.getCommittingStudentIndex() > 0);

        appModel.observe(AppModel.On.STUDENT_LOADED, students ->
                setEnabled(appModel.isStudentListLoaded() && appModel.getCommittingStudentIndex() > 0)
        );
        appModel.observe(AppModel.On.COMMIT_UPDATED, students ->
                setEnabled(appModel.isStudentListLoaded() && appModel.getCommittingStudentIndex() > 0)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.startCommitPrevious();
    }
}
