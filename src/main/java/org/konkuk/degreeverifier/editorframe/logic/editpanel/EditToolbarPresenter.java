package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditToolbar;

import static org.konkuk.degreeverifier.ui.Strings.EDIT_PANEL_TITLE;

public class EditToolbarPresenter {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final EditToolbar toolbar;

    public EditToolbarPresenter(EditToolbar editToolbar) {
        toolbar = editToolbar;
        setTitle();

        editorModel.observe(EditorModel.On.DEGREE_SELECTED, unused -> setTitle());
        editorModel.observe(EditorModel.On.NODES_SELECTED, unused -> setTitle());
    }

    private void setTitle() {
        String title = EDIT_PANEL_TITLE;
        if (editorModel.getSelectedDegrees().size() == 1) {
            title = editorModel.getSelectedDegree().toString() + " / ";
            if (editorModel.getSelectedNodeObjects().size() == 1
                    && !(editorModel.getSelectedNodeObject() instanceof EditableDegreeCriteria)) {
                title += editorModel.getSelectedNodeObject();
            }
        }
        toolbar.setTitle(title);
        toolbar.setToolTipText(title);
    }
}
