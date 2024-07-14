package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

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

        setEnabled(appModel.isTranscriptLoaded() && appModel.hasNextStudentToCommit());

        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, students ->
                setEnabled(appModel.isTranscriptLoaded() && appModel.hasNextStudentToCommit())
        );
        appModel.observe(AppModel.On.COMMIT_UPDATED, students ->
                setEnabled(appModel.isTranscriptLoaded() && appModel.hasNextStudentToCommit())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.startCommitNext();
    }
}
