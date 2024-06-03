package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.DECOMMIT_DEGREE;

public class DecommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public DecommitDegreeAction() {
        putValue(NAME, DECOMMIT_DEGREE);
        putValue(SHORT_DESCRIPTION, DECOMMIT_DEGREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/remove_icon.svg", getClass().getClassLoader()));

        appModel.observe(AppModel.ObserveOn.ON_COMMITTED_DEGREE_SELECTED, selected ->
                setEnabled(((List<DegreeSnapshot>)selected).size() >= 1)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.decommitDegrees();
    }
}
