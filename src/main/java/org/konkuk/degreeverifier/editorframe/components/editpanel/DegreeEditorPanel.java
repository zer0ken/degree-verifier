package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.common.components.IndentedGridCell;

import javax.swing.*;
import java.awt.*;

public class DegreeEditorPanel extends JPanel {
    public DegreeEditorPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        add(new JLabel("학위 이름:"), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        add(new JTextField(), gbc);
    }
}
