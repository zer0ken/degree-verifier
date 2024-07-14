package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.ShowEditorAction;
import org.konkuk.degreeverifier.common.components.ActionMenu;

import static org.konkuk.degreeverifier.ui.Strings.SETTING_MENU;

public class SettingMenu extends ActionMenu {
    public SettingMenu() {
        super(SETTING_MENU);

        add(new ShowEditorAction());
    }
}
