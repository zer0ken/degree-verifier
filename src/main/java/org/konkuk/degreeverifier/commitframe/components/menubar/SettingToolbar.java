package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.ShowEditorAction;
import org.konkuk.degreeverifier.common.components.SizedToolbar;

import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_BUTTON_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_ICON_SCALE;

public class SettingToolbar extends SizedToolbar {
    public SettingToolbar() {
        super(MENU_TOOLBAR_BUTTON_SIZE, MENU_TOOLBAR_ICON_SCALE);
        add(new ShowEditorAction());
    }
}
