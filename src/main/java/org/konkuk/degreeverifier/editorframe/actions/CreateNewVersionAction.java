package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.CREATE_NEW_VERSION;

public class CreateNewVersionAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public CreateNewVersionAction() {
        putValue(NAME, CREATE_NEW_VERSION);
        putValue(SHORT_DESCRIPTION, CREATE_NEW_VERSION);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/copy_icon.svg", getClass().getClassLoader()));

        setEnabled(editorModel.getSelectedDegrees() != null);
        editorModel.observe(EditorModel.On.DEGREE_SELECTED, selectedDegree -> setEnabled(selectedDegree != null));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editorModel.createNewVersionOfSelectedDegrees();
    }
}
