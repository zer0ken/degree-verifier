package org.konkuk.degreeverifier.common.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class SizedToolbar extends JToolBar {
    private final Dimension buttonSize;
    private final float iconScale;

    public SizedToolbar(Dimension buttonSize, float iconScale) {
        this.buttonSize = buttonSize;
        this.iconScale = iconScale;
    }

    public JButton add(Action a) {
        JButton button = super.add(a);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        FlatSVGIcon icon = (FlatSVGIcon) a.getValue(Action.LARGE_ICON_KEY);
        a.putValue(Action.LARGE_ICON_KEY, icon.derive(iconScale));
        return button;
    }
}
