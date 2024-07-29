package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.EXPORT_COMMIT_VALIDATE_OLD;

public class ExportCommitValidateOldAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ExportCommitValidateOldAction() {
        putValue(NAME, EXPORT_COMMIT_VALIDATE_OLD);
        putValue(SHORT_DESCRIPTION, EXPORT_COMMIT_VALIDATE_OLD);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/save_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExportCommitAction.defaultActionPerformed(e, AppModel.ExportMode.VALIDATE_OLD);
    }
}
