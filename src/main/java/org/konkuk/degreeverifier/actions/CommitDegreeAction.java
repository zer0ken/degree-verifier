package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Strings.COMMIT_VERIFIED_DEGREE;

public class CommitDegreeAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CommitDegreeAction() {
        putValue(NAME, COMMIT_VERIFIED_DEGREE);
        putValue(SHORT_DESCRIPTION, COMMIT_VERIFIED_DEGREE + " (Alt+Enter)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/done_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.ALT_DOWN_MASK));

        setEnabled(!appModel.getSelectedVerifiedDegree().isEmpty() &&
                !appModel.getCommittingStudent().getSufficientDegrees().isEmpty());

        appModel.observe(AppModel.On.VERIFIED_DEGREE_SELECTED, selected ->
                setEnabled(!((List<DegreeSnapshot>) selected).isEmpty() &&
                        !appModel.getCommittingStudent().getSufficientDegrees().isEmpty())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appModel.commitDegrees();
    }
}
