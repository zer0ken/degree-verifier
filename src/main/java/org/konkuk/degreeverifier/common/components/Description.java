package org.konkuk.degreeverifier.common.components;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.DESCRIPTION;

public class Description extends JLabel {
    public Description(String text) {
        super(text);

        setFont(getFont().deriveFont(getFont().getSize() - 1f));
        setForeground(DESCRIPTION);
    }
}
