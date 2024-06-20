package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.EDIT_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;

public class EditPanel_ extends JPanel {
    public EditPanel_() {
        setLayout(new GridBagLayout());
        setMinimumSize(MINIMUM_EDIT_PANEL_SIZE);
        setBorder(EDIT_PANEL_BORDER);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 0, 0, 0);

        add(new DegreeEditorPanel(), gbc);

        gbc.gridy++;
        add(new LabeledSeparator("교과목 검사 설정"), gbc);
        gbc.gridy++;
        add(new LectureEditorPanel(), gbc);

        gbc.gridy++;
        add(new LabeledSeparator("검사 그룹 설정"), gbc);
        gbc.gridy++;
        add(new CriteriaEditorPanel(), gbc);

    }
}
