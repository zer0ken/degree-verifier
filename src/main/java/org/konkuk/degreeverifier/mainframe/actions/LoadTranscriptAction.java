package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/sync_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(DefaultPaths.TRANSCRIPT_PATH));
        chooser.setDialogTitle(LOAD_TRANSCRIPT_DIALOG_TITLE);
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".csv");
            }

            @Override
            public String getDescription() {
                return "성적표 .csv 파일";
            }
        });
        int result = chooser.showOpenDialog(e != null? (Component) e.getSource() : null);
        if (result == JFileChooser.APPROVE_OPTION) {
            appModel.loadTranscript(chooser.getSelectedFile());
        }
    }
}
