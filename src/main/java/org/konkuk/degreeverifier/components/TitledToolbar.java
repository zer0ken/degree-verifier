package org.konkuk.degreeverifier.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.*;

public class TitledToolbar extends JToolBar {
    public TitledToolbar(String title, boolean buttonToRight) {
        setAlignmentY(JComponent.CENTER_ALIGNMENT);
        add(Box.createHorizontalStrut(TITLED_TOOLBAR_LEFT_INSET));
        add(new JLabel(title));
        if (buttonToRight) {
            add(Box.createGlue());
        } else {
            add(Box.createHorizontalStrut(TITLED_TOOLBAR_RIGHT_GAP));
        }
    }

    @Override
    public JButton add(Action a) {
        JButton button = super.add(a);
        button.setPreferredSize(SMALL_TOOLBAR_BUTTON_SIZE);
        button.setMinimumSize(SMALL_TOOLBAR_BUTTON_SIZE);
        button.setMaximumSize(SMALL_TOOLBAR_BUTTON_SIZE);
        FlatSVGIcon icon = (FlatSVGIcon) a.getValue(Action.LARGE_ICON_KEY);
        a.putValue(Action.LARGE_ICON_KEY, icon.derive(SMALL_TOOLBAR_ICON_SCALE));
        return button;
    }

    public TitledToolbar(String title) {
        this(title, true);
    }
}
