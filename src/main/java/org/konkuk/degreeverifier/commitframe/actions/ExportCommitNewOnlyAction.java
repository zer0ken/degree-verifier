package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.EXPORT_COMMIT_NEW_ONLY;

public class ExportCommitNewOnlyAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ExportCommitNewOnlyAction() {
        putValue(NAME, EXPORT_COMMIT_NEW_ONLY);
        putValue(SHORT_DESCRIPTION, EXPORT_COMMIT_NEW_ONLY);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/save_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         ExportCommitAction.defaultActionPerformed(e, AppModel.ExportMode.NEW_ONLY);
    }
}
