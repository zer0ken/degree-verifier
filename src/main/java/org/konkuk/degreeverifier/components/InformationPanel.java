package org.konkuk.degreeverifier.components;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_INFORMATION_PANEL_SIZE;

public class InformationPanel extends JPanel {
    public InformationPanel() {
        setMinimumSize(MINIMUM_INFORMATION_PANEL_SIZE);
        add(new JLabel("정보 표시용 패널입니다"));
    }
}
