package org.konkuk.degreeverifier.editorframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.konkuk.degreeverifier.ui.Strings.OPEN_VERIFIER_FILE;

public class OpenVerifierFileAction extends AbstractAction {
    private final EditorModel editorModel = EditorModel.getInstance();

    public OpenVerifierFileAction() {
        putValue(NAME, OPEN_VERIFIER_FILE);
        putValue(SHORT_DESCRIPTION, OPEN_VERIFIER_FILE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/file_icon.svg", getClass().getClassLoader()));

        setEnabled(editorModel.getSelectedDegrees().size() == 1);
        editorModel.observe(EditorModel.On.DEGREE_SELECTED, selectedDegrees ->
                setEnabled(((Collection<EditableDegreeCriteria>) selectedDegrees).size() == 1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            String path = DefaultPaths.VERIFIERS_PATH + "\\" + editorModel.getSelectedDegree().degreeName + ".json";
            try {
                File file = new File(path);
                Desktop.getDesktop().edit(file);
            } catch (IOException exception) {
                try {
                    Runtime.getRuntime().exec("notepad.exe \"" + path + "\"");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
