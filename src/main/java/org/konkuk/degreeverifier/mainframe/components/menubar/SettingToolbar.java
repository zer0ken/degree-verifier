package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.components.SizedToolbar;
import org.konkuk.degreeverifier.mainframe.actions.ShowEditorAction;

import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_BUTTON_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.MENU_TOOLBAR_ICON_SCALE;

public class SettingToolbar extends SizedToolbar {
    public SettingToolbar() {
        super(MENU_TOOLBAR_BUTTON_SIZE, MENU_TOOLBAR_ICON_SCALE);
        add(new ShowEditorAction());
    }
}
