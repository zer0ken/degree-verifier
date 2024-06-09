package org.konkuk.degreeverifier.mainframe.components.menubar;

import org.konkuk.degreeverifier.mainframe.actions.VerifyAllStudentAction;
import org.konkuk.degreeverifier.mainframe.actions.VerifyStudentAction;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_MENU;

public class VerifyMenu extends JMenu {
    public VerifyMenu() {
        super(VERIFY_MENU);

        add(new VerifyStudentAction());
        add(new VerifyAllStudentAction());
    }
}
