package org.konkuk.degreeverifier.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.LOAD_VERIFIER_LIST;

public class LoadVerifierAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public LoadVerifierAction() {
        putValue(NAME, LOAD_VERIFIER_LIST);
        putValue(SHORT_DESCRIPTION, LOAD_VERIFIER_LIST);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/sync_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.loadVerifier();
    }
}
