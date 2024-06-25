package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.Editable;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.CONFIRM_REMOVE_RECURSIVE_MESSAGE;
import static org.konkuk.degreeverifier.ui.Strings.REMOVE_RECURSIVE_VERIFIER;

public class RemoveRecursiveVerifierAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public RemoveRecursiveVerifierAction() {
        putValue(NAME, REMOVE_RECURSIVE_VERIFIER);
        putValue(SHORT_DESCRIPTION, REMOVE_RECURSIVE_VERIFIER);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/remove_icon.svg", getClass().getClassLoader()));

        setEnabled(!editorModel.getSelectedNodeObjects().isEmpty()
                && !(editorModel.getSelectedNodeObject() instanceof EditableDegreeCriteria));
        editorModel.observe(EditorModel.On.NODES_SELECTED, unused ->
                setEnabled(!editorModel.getSelectedNodeObjects().isEmpty()
                        && !(editorModel.getSelectedNodeObject() instanceof EditableDegreeCriteria))
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog((Component) e.getSource(), CONFIRM_REMOVE_RECURSIVE_MESSAGE, REMOVE_RECURSIVE_VERIFIER, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (Editable selectedNodeObject : editorModel.getSelectedNodeObjects()) {
                EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) selectedNodeObject;
                recursive.removed = true;
            }
        }
    }
}
