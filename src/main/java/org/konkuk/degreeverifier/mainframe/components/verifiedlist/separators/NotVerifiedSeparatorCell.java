package org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static org.konkuk.degreeverifier.ui.Colors.NOT_VERIFIED_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.NOT_VERIFIED_DEGREES;

public class NotVerifiedSeparatorCell extends SeparatorCell {
    public NotVerifiedSeparatorCell() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(UIManager.getColor("List.background"));
        setForeground(NOT_VERIFIED_SEPARATOR_FOREGROUND);
        setBorder(new EmptyBorder(3, 16, 3, 16));

        setText(NOT_VERIFIED_DEGREES);
    }
}
