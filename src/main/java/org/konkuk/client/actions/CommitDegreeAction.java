package org.konkuk.client.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.client.AppModel;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.client.ui.Strings.COMMIT_VERIFIED_DEGREE;

public class CommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitDegreeAction() {
        putValue(NAME, COMMIT_VERIFIED_DEGREE);
        putValue(SHORT_DESCRIPTION, COMMIT_VERIFIED_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("org/konkuk/icons/done_icon.svg", getClass().getClassLoader()));

        appModel.observe(AppModel.ObserveOf.ON_STUDENT_SELECTED, (unused) ->
                setEnabled(!appModel.getSelectedStudents().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.commit();
    }
}
