package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.editorframe.EditorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class CancelChangesAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public CancelChangesAction() {
        putValue(NAME, CANCEL_EDIT);
        putValue(SHORT_DESCRIPTION, CANCEL_EDIT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/clear_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog((Component) e.getSource(), CONFIRM_ROLLBACK_EDIT_MESSAGE, ROLLBACK_EDIT, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            editorModel.loadVerifiers();
            EditorFrame.getInstance().setVisible(false);
        }
    }
}
