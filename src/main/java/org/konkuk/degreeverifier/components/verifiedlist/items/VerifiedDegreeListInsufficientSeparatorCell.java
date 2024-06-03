package org.konkuk.degreeverifier.components.verifiedlist.items;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.INSUFFICIENT_DEGREES;

public class VerifiedDegreeListInsufficientSeparatorCell extends JPanel {
    public VerifiedDegreeListInsufficientSeparatorCell() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(UIManager.getColor("List.background"));
        setForeground(INSUFFICIENT_SEPARATOR_FOREGROUND);
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
