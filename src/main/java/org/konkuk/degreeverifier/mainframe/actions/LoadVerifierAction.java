package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static org.konkuk.degreeverifier.ui.Strings.LOAD_VERIFIER_LIST;

public class LoadVerifierAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public LoadVerifierAction() {
        putValue(NAME, LOAD_VERIFIER_LIST);
        putValue(SHORT_DESCRIPTION, LOAD_VERIFIER_LIST);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/sync_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (getSelectedFile().isDirectory()) {
                    super.approveSelection();
                }
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(DefaultPaths.VERIFIERS_PATH));
        chooser.setSelectedFile(new File(DefaultPaths.VERIFIERS_PATH));
        int result = chooser.showOpenDialog((Component) e.getSource());
        if (result == JFileChooser.APPROVE_OPTION) {
            appModel.loadVerifiers(chooser.getSelectedFile().getAbsolutePath());
        }
    }
}
