package org.konkuk.degreeverifier.common.components;

import javax.swing.*;
import java.awt.*;

public class IndentedGridCell extends JPanel {
    public IndentedGridCell(JComponent component, int indent) {
        setLayout(new BorderLayout());
        add(component);
        add(Box.createHorizontalStrut(22 * indent), BorderLayout.WEST);
    }
}
