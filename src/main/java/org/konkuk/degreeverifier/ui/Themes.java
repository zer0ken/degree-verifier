package org.konkuk.degreeverifier.ui;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

public class Themes {
    public static boolean setup() {
        boolean succeed = FlatIntelliJLaf.setup();

        if (succeed) {
            UIManager.put("Tree.selectionArc", 8);
            UIManager.put("Component.arrowType", "chevron");
            UIManager.put("Tree.rendererMargins", new Insets(3, 18, 3, 8));
            UIManager.put("Tree.selectionInsets", new Insets(0, 8, 0, 8));
            UIManager.put("Tree.leftChildIndent", 16);
            UIManager.put("Tree.selectionBackground", new Color(0xd4e2ff));
            UIManager.put("Tree.selectionInactiveBackground", new Color(0xdfe1e5));
            UIManager.put("Tree.selectionForeground", UIManager.get("Tree.foreground"));

            UIManager.put("List.selectionArc", 8);
            UIManager.put("List.cellMargins", new Insets(3, 18, 3, 8));
            UIManager.put("List.selectionInsets", new Insets(0, 8, 0, 8));
            UIManager.put("List.selectionBackground", new Color(0xd4e2ff));
            UIManager.put("Tree.selectionInactiveBackground", new Color(0xdfe1e5));
            UIManager.put("List.selectionForeground", UIManager.get("List.foreground"));

            UIManager.put("SplitPaneDivider.style", "plain");

            UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
        }

        return succeed;
    }
}
