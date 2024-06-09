package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class CommitAllStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitAllStudentAction() {
        putValue(NAME, COMMIT_ALL_STUDENT);
        putValue(SHORT_DESCRIPTION, COMMIT_ALL_STUDENT + " (F1)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/play_arrow_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        setEnabled(appModel.isStudentListLoaded() && appModel.isVerifierLoaded());

        appModel.observe(AppModel.On.STUDENT_LOADED, students ->
                setEnabled(appModel.isStudentListLoaded() && appModel.isVerifierLoaded()));
        appModel.observe(AppModel.On.VERIFIER_LOADED, students ->
                setEnabled(appModel.isStudentListLoaded() && appModel.isVerifierLoaded()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int select = JOptionPane.showConfirmDialog(null,
                COMMIT_ALL_STUDENT_MESSAGE, COMMIT_RECOMMENDED_DEGREES_DIALOG_TITLE,
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (select == JOptionPane.CANCEL_OPTION || select == JOptionPane.CLOSED_OPTION) {
            return;
        }
        appModel.commitAllStudentAutomatically(select == JOptionPane.YES_OPTION);
    }
}
