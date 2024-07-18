package org.konkuk.degreeverifier.common.components;

import javax.swing.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class ScrollPaneWrapper {
    public static JScrollPane wrapTable(JTable table) {
        JScrollPane scrollPane = new JScrollPane(
                table,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
}
