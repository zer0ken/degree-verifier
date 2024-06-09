package org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static org.konkuk.degreeverifier.ui.Colors.SUFFICIENT_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.SUFFICIENT_DEGREES;

public class SufficientSeparatorCell extends SeparatorCell {
    public SufficientSeparatorCell() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(UIManager.getColor("List.background"));
        setForeground(SUFFICIENT_SEPARATOR_FOREGROUND);
        setBorder(new EmptyBorder(3, 16, 3, 16));

        setText(SUFFICIENT_DEGREES);
    }
}
