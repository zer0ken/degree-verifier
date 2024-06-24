package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class CreateDegreeVerifierAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public CreateDegreeVerifierAction() {
        putValue(NAME, CREATE_DEGREE_VERIFIER);
        putValue(SHORT_DESCRIPTION, CREATE_DEGREE_VERIFIER);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/add_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editorModel.createDegree();
    }
}
