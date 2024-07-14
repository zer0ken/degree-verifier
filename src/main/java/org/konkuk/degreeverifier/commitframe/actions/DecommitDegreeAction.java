package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.DECOMMIT_DEGREE;

public class DecommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public DecommitDegreeAction() {
        putValue(NAME, DECOMMIT_DEGREE);
        putValue(SHORT_DESCRIPTION, DECOMMIT_DEGREE + " (Alt+Backspace)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/remove_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.ALT_DOWN_MASK));

        setEnabled(!appModel.getSelectedCommittedDegree().isEmpty());

        appModel.observe(AppModel.On.COMMITTED_DEGREE_SELECTED, selected ->
                setEnabled(!((List<DegreeSnapshot>) selected).isEmpty())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.decommitDegrees();
    }
}
