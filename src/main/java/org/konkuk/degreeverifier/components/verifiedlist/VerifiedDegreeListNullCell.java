package org.konkuk.degreeverifier.components.verifiedlist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.COMMON_RED;
import static org.konkuk.degreeverifier.ui.Strings.INSUFFICIENT_DEGREES;

public class VerifiedDegreeListNullCell extends JPanel {
    public VerifiedDegreeListNullCell() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(UIManager.getColor("List.background"));
        setForeground(COMMON_RED);
        setBorder(new EmptyBorder(3, 16, 3, 16));

        addSeparator();
        add(Box.createHorizontalStrut(8));
        addLabel();
        add(Box.createHorizontalStrut(8));
        addSeparator();
    }

    private void addSeparator(){
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(getForeground());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(separator, gbc);
        panel.setBackground(getBackground());
        add(panel);
    }

    private void addLabel(){
        JLabel label = new JLabel(INSUFFICIENT_DEGREES);
        label.setForeground(getForeground());
        label.setAlignmentY(CENTER_ALIGNMENT);
        add(label);
    }
}
