package org.konkuk.degreeverifier.common.components;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneWrapper {
    public static JScrollPane wrapTable(Component component) {
        JScrollPane scrollPane = new JScrollPane(
                component,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
}
