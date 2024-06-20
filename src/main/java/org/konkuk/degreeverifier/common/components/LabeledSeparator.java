package org.konkuk.degreeverifier.common.components;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.LABELED_SEPARATOR_BORDER;
import static org.konkuk.degreeverifier.ui.Borders.LABELED_SEPARATOR_BORDER2;
import static org.konkuk.degreeverifier.ui.Dimensions.MAXIMUM_LABELED_SEPARATOR_SIZE;

public class LabeledSeparator extends JPanel {
    public LabeledSeparator() {
        setBorder(LABELED_SEPARATOR_BORDER);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(MAXIMUM_LABELED_SEPARATOR_SIZE);
    }

    public LabeledSeparator(String text) {
        this();
        setBorder(LABELED_SEPARATOR_BORDER2);
        setForeground(UIManager.getColor("Separator.foreground"));

        addLabel(text);
        add(Box.createHorizontalStrut(8));
        addSeparator();
    }

    public LabeledSeparator(String text, Color foreground, Color background) {
        this();
        setForeground(foreground);
        setBackground(background);

        addSeparator();
        add(Box.createHorizontalStrut(8));
        addLabel(text, getForeground());
        add(Box.createHorizontalStrut(8));
        addSeparator();
    }

    protected void addSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(getForeground());
        addCenter(separator);
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        add(label);
    }

    private void addLabel(String text, Color foreground) {
        JLabel label = new JLabel(text);
        label.setForeground(foreground);
        add(label);
    }

    private void addCenter(JComponent component) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
        panel.setBackground(getBackground());
        add(panel);
    }
}
