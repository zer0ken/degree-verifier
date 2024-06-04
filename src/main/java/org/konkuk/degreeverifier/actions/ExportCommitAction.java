package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.EXPORT_COMMITTED_DEGREE;

public class ExportCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ExportCommitAction() {
        putValue(NAME, EXPORT_COMMITTED_DEGREE);
        putValue(SHORT_DESCRIPTION, EXPORT_COMMITTED_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/save_icon.svg", getClass().getClassLoader()));

        setEnabled(appModel.getCommittingStudent() != null &&
                appModel.getCommittingStudent().isVerified());

        appModel.observe(AppModel.On.COMMIT_UPDATED, student ->
                setEnabled(((Student) student).isVerified()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.exportManually();
    }
}
