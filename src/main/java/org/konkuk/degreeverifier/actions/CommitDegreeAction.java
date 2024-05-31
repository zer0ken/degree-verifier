package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_VERIFIED_DEGREE;

public class CommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitDegreeAction() {
        putValue(NAME, COMMIT_VERIFIED_DEGREE);
        putValue(SHORT_DESCRIPTION, COMMIT_VERIFIED_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/done_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.commitDegrees();
    }
}
