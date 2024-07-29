package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.EXPORT_COMMIT_NEW_AND_OLD;

public class ExportCommitNewAndOldAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ExportCommitNewAndOldAction() {
        putValue(NAME, EXPORT_COMMIT_NEW_AND_OLD);
        putValue(SHORT_DESCRIPTION, EXPORT_COMMIT_NEW_AND_OLD);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/save_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExportCommitAction.defaultActionPerformed(e, AppModel.ExportMode.NEW_AND_OLD);
    }
}
