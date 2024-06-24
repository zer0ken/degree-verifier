package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.CONFIRM_REMOVE_DEGREE_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.REMOVE_DEGREE_VERIFIER;

public class RemoveDegreeVerifierAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public RemoveDegreeVerifierAction() {
        putValue(NAME, REMOVE_DEGREE_VERIFIER);
        putValue(SHORT_DESCRIPTION, REMOVE_DEGREE_VERIFIER);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/remove_icon.svg", getClass().getClassLoader()));

        setEnabled(editorModel.getSelectedDegrees() != null);
        editorModel.observe(EditorModel.On.DEGREE_SELECTED, selectedDegree -> setEnabled(selectedDegree != null));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog((Component) e.getSource(), CONFIRM_REMOVE_DEGREE_MESSAGE, REMOVE_DEGREE_VERIFIER, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            editorModel.removeSelectedDegree();
        }
    }
}
