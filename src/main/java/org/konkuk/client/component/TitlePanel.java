package org.konkuk.client.component;

import javax.swing.*;

import java.awt.*;

import static org.konkuk.client.ui.Dimensions.TITLE_PANEL_PADDING;

public class TitlePanel extends JPanel {
    public TitlePanel(String title) {
        setBorder(TITLE_PANEL_PADDING);
        setLayout(new BorderLayout());

        add(new JLabel(title), BorderLayout.NORTH);
    }
}
