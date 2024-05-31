package org.konkuk.client.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.client.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.client.ui.Strings.COMMIT_VERIFIED_DEGREES;

public class StartCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public StartCommitAction() {
        putValue(NAME, COMMIT_VERIFIED_DEGREES);
        putValue(SHORT_DESCRIPTION, COMMIT_VERIFIED_DEGREES);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("org/konkuk/icons/commit_student.svg", getClass().getClassLoader()));

        appModel.observe(AppModel.ObserveOf.ON_STUDENT_SELECTED, (unused) ->
                setEnabled(!appModel.getSelectedStudents().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.setCommittingStudent(appModel.getSelectedStudents().get(0));
    }
}
