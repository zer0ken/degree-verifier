package org.konkuk.degreeverifier.commitframe.components.menubar;

import org.konkuk.degreeverifier.commitframe.actions.VerifyAllStudentAction;
import org.konkuk.degreeverifier.commitframe.actions.VerifyStudentAction;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Strings.VERIFY_MENU;

public class VerifyMenu extends JMenu {
    public VerifyMenu() {
        super(VERIFY_MENU);

        add(new VerifyStudentAction());
        add(new VerifyAllStudentAction());
    }
}
