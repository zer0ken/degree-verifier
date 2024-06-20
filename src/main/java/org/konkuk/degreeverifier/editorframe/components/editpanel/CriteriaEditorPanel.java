package org.konkuk.degreeverifier.editorframe.components.editpanel;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Strings.CRITERIA_VERIFIER;

public class CriteriaEditorPanel extends JPanel {
    public CriteriaEditorPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        add(new JCheckBox(CRITERIA_VERIFIER), gbc);
    }
}
