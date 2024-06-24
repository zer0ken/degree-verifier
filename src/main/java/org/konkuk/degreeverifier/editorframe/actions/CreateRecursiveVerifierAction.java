package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.CREATE_RECURSIVE_VERIFIER;

public class CreateRecursiveVerifierAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public CreateRecursiveVerifierAction() {
        putValue(NAME, CREATE_RECURSIVE_VERIFIER);
        putValue(SHORT_DESCRIPTION, CREATE_RECURSIVE_VERIFIER);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/add_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: 2024-06-24 implement this
    }
}
