package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.ROLLBACK_SELECTED_NODE;

public class RollbackSelectedNodeAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public RollbackSelectedNodeAction() {
        putValue(NAME, ROLLBACK_SELECTED_NODE);
        putValue(SHORT_DESCRIPTION, ROLLBACK_SELECTED_NODE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/undo_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: 2024-06-24 implement this
    }
}
