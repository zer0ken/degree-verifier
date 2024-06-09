package org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.INSUFFICIENT_DEGREES;

public class InsufficientSeparatorCell extends SeparatorCell {
    public InsufficientSeparatorCell() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(UIManager.getColor("List.background"));
        setForeground(INSUFFICIENT_SEPARATOR_FOREGROUND);
        setBorder(new EmptyBorder(3, 16, 3, 16));

        setText(INSUFFICIENT_DEGREES);
    }
}
