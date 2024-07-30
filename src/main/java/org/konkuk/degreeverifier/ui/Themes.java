package org.konkuk.degreeverifier.ui;

import com.formdev.flatlaf.FlatLightLaf;
import org.konkuk.degreeverifier.common.components.OutlinedFlatRoundBorder;
import org.konkuk.degreeverifier.common.components.OutlinedFlatTextBorder;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.util.Locale;

import static org.konkuk.degreeverifier.ui.Colors.DEFAULT_BACKGROUND;

public class Themes {
    public static boolean setup() {
        Locale.setDefault(Locale.KOREA);

        boolean succeed = FlatLightLaf.setup();

        if (succeed) {
            UIManager.put("RootPane.background", new ColorUIResource(DEFAULT_BACKGROUND));

            UIManager.put("Panel.background", UIManager.getColor("RootPane.background"));
            UIManager.put("SplitPane.background", UIManager.getColor("RootPane.background"));
            UIManager.put("ToolBar.background", UIManager.getColor("RootPane.background"));
            UIManager.put("Component.background", UIManager.getColor("RootPane.background"));
            UIManager.put("OptionPane.background", UIManager.getColor("RootPane.background"));
            UIManager.put("TabbedPane.background", UIManager.getColor("RootPane.background"));

            UIManager.put("TitlePane.embeddedForeground", new Color(0x494949));

            UIManager.put("Component.borderWidth", 0);
            UIManager.put("Component.focusWidth", 1);
            UIManager.put("Component.innerFocusWidth", 0);

            UIManager.put("Component.arrowType", "chevron");
            UIManager.put("Tree.selectionArc", 8);
            UIManager.put("Tree.rendererMargins", new Insets(3, 18, 3, 18));
            UIManager.put("Tree.selectionInsets", new Insets(0, 8, 0, 8));
            UIManager.put("Tree.leftChildIndent", 16);
            UIManager.put("Tree.selectionBackground", new Color(0xd4e2ff));
            UIManager.put("Tree.selectionInactiveBackground", new Color(0xdfe1e5));
            UIManager.put("Tree.selectionForeground", UIManager.get("Tree.foreground"));

            UIManager.put("List.selectionArc", 8);
            UIManager.put("List.cellMargins", new Insets(3, 18, 3, 18));
            UIManager.put("List.selectionInsets", new Insets(0, 8, 0, 8));
            UIManager.put("List.selectionBackground", new Color(0xd4e2ff));
            UIManager.put("Tree.selectionInactiveBackground", new Color(0xdfe1e5));
            UIManager.put("List.selectionForeground", UIManager.get("List.foreground"));

            UIManager.put("SplitPaneDivider.style", "plain");

            UIManager.put("CheckBox.icon.borderWidth", 1);

            UIManager.put("ComboBox.border", new OutlinedFlatRoundBorder());
            UIManager.put("Spinner.border", new OutlinedFlatRoundBorder());
            UIManager.put("TextField.border", new OutlinedFlatTextBorder());
        }

        return succeed;
    }
}
