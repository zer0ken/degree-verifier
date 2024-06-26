package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;

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

        setEnabled(editorModel.getSelectedNodeObjects().size() == 1
                && editorModel.getSelectedNodeObject() instanceof EditableRecursiveCriteria
                && ((EditableRecursiveCriteria) editorModel.getSelectedNodeObject()).lectureCriteria == null);
        editorModel.observe(EditorModel.On.NODES_SELECTED, unused ->
                setEnabled(editorModel.getSelectedNodeObjects().size() == 1
                        && editorModel.getSelectedNodeObject() instanceof EditableRecursiveCriteria
                        && ((EditableRecursiveCriteria) editorModel.getSelectedNodeObject()).lectureCriteria == null)
        );
        editorModel.observe(EditorModel.On.DEGREE_UPDATED, unused ->
                setEnabled(editorModel.getSelectedNodeObjects().size() == 1
                        && editorModel.getSelectedNodeObject() instanceof EditableRecursiveCriteria
                        && ((EditableRecursiveCriteria) editorModel.getSelectedNodeObject()).lectureCriteria == null)
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
        recursive.createSubcriteria();
        editorModel.notifyUpdatedSelectedDegree();
    }
}
