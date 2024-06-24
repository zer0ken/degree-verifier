package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.common.components.ActionMenu;
import org.konkuk.degreeverifier.mainframe.actions.ShowEditorAction;

import static org.konkuk.degreeverifier.ui.Strings.SETTING_MENU;

public class SettingMenu extends ActionMenu {
    public SettingMenu() {
        super(SETTING_MENU);

        add(new ShowEditorAction());
    }
}
