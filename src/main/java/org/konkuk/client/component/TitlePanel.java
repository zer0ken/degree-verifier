package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.client.ui.Dimensions.TITLE_PANNEL_PADDING;

public class TitlePanel extends JPanel {
    public TitlePanel(String title) {
        setLayout(new BorderLayout());
        setBorder(TITLE_PANNEL_PADDING);

        add(new JLabel(title), BorderLayout.NORTH);
    }
}
