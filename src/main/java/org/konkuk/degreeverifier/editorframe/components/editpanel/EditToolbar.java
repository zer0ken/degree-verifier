package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.common.components.TitledToolbar;
import org.konkuk.degreeverifier.editorframe.logic.editpanel.EditToolbarPresenter;

import static org.konkuk.degreeverifier.ui.Strings.EDIT_PANEL_TITLE;

public class EditToolbar extends TitledToolbar {
    public EditToolbar() {
        super(EDIT_PANEL_TITLE);
        new EditToolbarPresenter(this);
    }
}
