package org.konkuk.degreeverifier.components.statusbar;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.STATUS_BAR_PADDING;
import static org.konkuk.degreeverifier.ui.Dimensions.STATUS_BAR_SIZE;

public class StatusBar extends JPanel {
    public StatusBar() {
        setLayout(new BorderLayout());
        setPreferredSize(STATUS_BAR_SIZE);
//        setBorder(TOP_SEPARATOR);

        JPanel inner = new JPanel(new BorderLayout());
        inner.setBorder(STATUS_BAR_PADDING);

        JToolBar left = new JToolBar();
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder());
        left.setAlignmentY(CENTER_ALIGNMENT);
        inner.add(left, BorderLayout.WEST);

        JToolBar right = new JToolBar();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder());
        right.setAlignmentY(CENTER_ALIGNMENT);
        inner.add(right, BorderLayout.EAST);

        add(inner);

        // add here
        // left.add
        right.add(new ProgressStatusPanel(), 0);
    }
}
