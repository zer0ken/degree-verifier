package org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators;

import javax.swing.*;
import java.awt.*;

public class SeparatorCell extends JPanel {
    protected void setText(String text) {
        addSeparator();
        add(Box.createHorizontalStrut(8));
        addLabel(text);
        add(Box.createHorizontalStrut(8));
        addSeparator();
    }

    protected void addSeparator(){
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

    private void addLabel(String text){
        JLabel label = new JLabel(text);
        label.setForeground(getForeground());
        label.setAlignmentY(CENTER_ALIGNMENT);
        add(label);
    }
}
