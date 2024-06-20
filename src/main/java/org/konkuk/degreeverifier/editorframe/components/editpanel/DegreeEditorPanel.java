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
        gbc.weightx = 0.3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        add(new IndentedGridCell(new JLabel("학위 이름:"), 1), gbc);

        gbc.gridx++;
        gbc.weightx = 1.0;
        add(new JTextField(), gbc);
    }
}
