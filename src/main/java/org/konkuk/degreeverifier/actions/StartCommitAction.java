package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.START_COMMIT;

public class StartCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitAction() {
        putValue(NAME, START_COMMIT);
        putValue(SHORT_DESCRIPTION, START_COMMIT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/commit_student.svg", getClass().getClassLoader()));

        appModel.observe(AppModel.ObserveOf.ON_STUDENT_SELECTED, (unused) ->
                setEnabled(!appModel.getSelectedStudents().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.startCommit();
    }
}
