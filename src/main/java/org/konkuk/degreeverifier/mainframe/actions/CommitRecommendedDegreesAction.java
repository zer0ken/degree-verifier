package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.student.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class CommitRecommendedDegreesAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitRecommendedDegreesAction() {
        putValue(NAME, COMMIT_RECOMMENDED_DEGREES);
        putValue(SHORT_DESCRIPTION, COMMIT_RECOMMENDED_DEGREES + " (F3)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/done_all_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

        setEnabled(appModel.getCommittingStudent() != null && appModel.getCommittingStudent().isVerified());

        appModel.observe(AppModel.On.COMMIT_UPDATED, student ->
                setEnabled(!((Student) student).getSufficientDegrees().isEmpty()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String list = "<ul>";
        for (String s : appModel.getCommittingStudent().getRecommendedBundle().keySet()) {
            list += "<li>" + s + "</li>";
        }
        list += "</ul>";
        int confirm = JOptionPane.showConfirmDialog((Component) e.getSource(),
                String.format(COMMIT_RECOMMENDED_DEGREES_DIALOG_MESSAGE, list), COMMIT_RECOMMENDED_DEGREES_DIALOG_TITLE,
                JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            appModel.commitRecommendedDegrees();
        }
    }
}
