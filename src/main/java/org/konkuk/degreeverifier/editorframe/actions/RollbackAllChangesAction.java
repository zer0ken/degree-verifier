package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.ROLLBACK_ALL;

public class RollbackAllChangesAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public RollbackAllChangesAction() {
        putValue(NAME, ROLLBACK_ALL);
        putValue(SHORT_DESCRIPTION, ROLLBACK_ALL);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/undo_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: 2024-06-24 implement this
    }
}
