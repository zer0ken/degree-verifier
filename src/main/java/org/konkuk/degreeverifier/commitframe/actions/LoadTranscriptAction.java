package org.konkuk.degreeverifier.commitframe.actions;

import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static org.konkuk.degreeverifier.ui.Strings.LOAD_TRANSCRIPT;
import static org.konkuk.degreeverifier.ui.Strings.LOAD_TRANSCRIPT_DIALOG_TITLE;

public class LoadTranscriptAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public LoadTranscriptAction() {
        putValue(NAME, LOAD_TRANSCRIPT);
        putValue(SHORT_DESCRIPTION, LOAD_TRANSCRIPT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (getSelectedFile().getName().toLowerCase().endsWith(".csv") && getSelectedFile().exists()) {
                    super.approveSelection();
                }
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(DefaultPaths.TRANSCRIPT_PATH));
        chooser.setDialogTitle(LOAD_TRANSCRIPT_DIALOG_TITLE);
        int result = chooser.showOpenDialog(e != null ? (Component) e.getSource() : null);
        if (result == JFileChooser.APPROVE_OPTION) {
            appModel.loadTranscript(chooser.getSelectedFile());
        }
    }
}
