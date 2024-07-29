package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.editorframe.EditorFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.konkuk.degreeverifier.ui.Strings.EDIT_FRAME_TITLE;

public class ShowEditorAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ShowEditorAction() {
        putValue(NAME, EDIT_FRAME_TITLE);
        putValue(SHORT_DESCRIPTION, EDIT_FRAME_TITLE + " (F9)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/rule_thin_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));

        setEnabled(appModel.isVerifierLoaded());
        appModel.observe(AppModel.On.VERIFIER_LOADED, unused -> setEnabled(appModel.isVerifierLoaded()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditorFrame.getInstance().setVisible(true);
    }
}
