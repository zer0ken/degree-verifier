package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_RECOMMENDED_VERIFIED_DEGREE;

public class CommitRecommendedDegreesAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitRecommendedDegreesAction() {
        putValue(NAME, COMMIT_RECOMMENDED_VERIFIED_DEGREE);
        putValue(SHORT_DESCRIPTION, COMMIT_RECOMMENDED_VERIFIED_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/done_all_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.commitRecommendedDegrees();
    }
}
