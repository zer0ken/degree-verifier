package org.konkuk.degreeverifier.commitframe.actions;

import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static org.konkuk.degreeverifier.ui.Strings.LOAD_ALIASES;
import static org.konkuk.degreeverifier.ui.Strings.LOAD_ALIASES_DIALOG_TITLE;

public class LoadAliasesAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public LoadAliasesAction() {
        putValue(NAME, LOAD_ALIASES);
        putValue(SHORT_DESCRIPTION, LOAD_ALIASES);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (getSelectedFile().getName().toLowerCase().endsWith(".csv")) {
                    super.approveSelection();
                }
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(DefaultPaths.ALIASES_PATH));
        chooser.setDialogTitle(LOAD_ALIASES_DIALOG_TITLE);
        int result = chooser.showOpenDialog(e != null? (Component) e.getSource() : null);
        if (result == JFileChooser.APPROVE_OPTION) {
            appModel.loadAliases(chooser.getSelectedFile());
            if ((e != null ? e.getSource() : null) instanceof JButton) {
                ((JButton) e.getSource()).setText(chooser.getSelectedFile().getAbsolutePath());
                ((JButton) e.getSource()).setToolTipText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
