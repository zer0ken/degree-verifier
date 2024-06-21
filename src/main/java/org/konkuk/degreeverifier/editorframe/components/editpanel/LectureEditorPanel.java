package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.common.components.IndentedGridCell;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Strings.LECTURE_VERIFIER;

public class LectureEditorPanel extends JPanel {
    private final JTextField lectureNameField = new JTextField();

    public LectureEditorPanel() {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
        add(new IndentedGridCell(new JCheckBox(LECTURE_VERIFIER), 1), gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(new JLabel("과목명:"), 2), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(lectureNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(new JCheckBox("과목명:"), 1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(new JTextField(), gbc);
    }
}
