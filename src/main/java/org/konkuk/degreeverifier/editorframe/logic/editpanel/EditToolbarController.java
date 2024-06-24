package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditToolbar;

import static org.konkuk.degreeverifier.ui.Strings.EDIT_PANEL_TITLE;

public class EditToolbarController {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final EditToolbar toolbar;

    public EditToolbarController(EditToolbar editToolbar) {
        toolbar = editToolbar;
        setTitle();

        editorModel.observe(EditorModel.On.DEGREE_SELECTED, unused -> setTitle());
        editorModel.observe(EditorModel.On.NODE_SELECTED, unused -> setTitle());
    }

    private void setTitle() {
        String title = EDIT_PANEL_TITLE;
        if (editorModel.getSelectedDegree() != null) {
            title = editorModel.getSelectedDegree().toString() + " / ";
            if (editorModel.getSelectedNodes().size() == 1
                    && !(editorModel.getSelectedNodes().get(0) instanceof EditableDegreeCriteria)) {
                title += editorModel.getSelectedNodes().get(0);
            }
        }
        toolbar.setTitle(title);
        toolbar.setToolTipText(title);
    }
}
