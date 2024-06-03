package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_VERIFIED_DEGREE;

public class CommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitDegreeAction() {
        putValue(NAME, COMMIT_VERIFIED_DEGREE);
        putValue(SHORT_DESCRIPTION, COMMIT_VERIFIED_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/done_icon.svg", getClass().getClassLoader()));

        setEnabled(!appModel.getSelectedVerifiedDegree().isEmpty());

        appModel.observe(AppModel.ObserveOn.ON_VERIFIED_DEGREE_SELECTED, selected ->
                setEnabled(!((List<DegreeSnapshot>) selected).isEmpty())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.commitDegrees();
    }
}
