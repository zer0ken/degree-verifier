package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.Editable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.CONFIRM_ROLLBACK_SELECTED_NODE_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.ROLLBACK_SELECTED_NODE;

public class RollbackSelectedNodeAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public RollbackSelectedNodeAction() {
        putValue(NAME, ROLLBACK_SELECTED_NODE);
        putValue(SHORT_DESCRIPTION, ROLLBACK_SELECTED_NODE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/undo_icon.svg", getClass().getClassLoader()));

        setEnabled(!editorModel.getRollbackableSelectedNodeObjects().isEmpty());
        editorModel.observe(EditorModel.On.NODES_SELECTED, unused ->
                setEnabled(!editorModel.getRollbackableSelectedNodeObjects().isEmpty())
        );
        editorModel.observe(EditorModel.On.DEGREE_UPDATED, unused ->
                setEnabled(!editorModel.getRollbackableSelectedNodeObjects().isEmpty())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog((Component) e.getSource(), CONFIRM_ROLLBACK_SELECTED_NODE_MESSAGE, ROLLBACK_SELECTED_NODE, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (Editable rollbackableSelectedNodeObject : editorModel.getRollbackableSelectedNodeObjects()) {
                rollbackableSelectedNodeObject.rollback();
            }
            editorModel.notifyUpdatedSelectedDegree();
        }
    }
}
