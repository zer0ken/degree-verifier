package org.konkuk.degreeverifier.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Borders.TITLED_TOOLBAR_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.*;

public class TitledToolbar extends JToolBar {
    private final List<Component> whenNarrow = new LinkedList<>();
    private final List<Component> whenWide = new LinkedList<>();

    private boolean narrow = true;

    public TitledToolbar(String title) {
        setName(title);
        setPreferredSize(TITLED_TOOLBAR_SIZE);
        setAlignmentY(JComponent.CENTER_ALIGNMENT);
        setBorder(TITLED_TOOLBAR_BORDER);
        add(new JLabel(title));

        whenNarrow.add(add(Box.createGlue()));
        whenWide.add(add(Box.createHorizontalStrut(TITLED_TOOLBAR_RIGHT_GAP)));

        TitledToolbar toolbar = this;
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                boolean _narrow = toolbar.getWidth() <= TITLED_TOOLBAR_NARROW_LIMIT;
                if (narrow != _narrow) {
                    setNarrow(_narrow);
                }
            }
        });
    }

    private void setNarrow(boolean narrow) {
        if (narrow) {
            whenNarrow.forEach((c) -> c.setVisible(true));
            whenWide.forEach((c) -> c.setVisible(false));
        } else {
            whenWide.forEach((c) -> c.setVisible(true));
            whenNarrow.forEach((c) -> c.setVisible(false));
        }
        this.narrow = narrow;
    }

    @Override
    public JButton add(Action a) {
        JButton button = super.add(a);
        button.setPreferredSize(TITLED_TOOLBAR_BUTTON_SIZE);
        button.setMinimumSize(TITLED_TOOLBAR_BUTTON_SIZE);
        button.setMaximumSize(TITLED_TOOLBAR_BUTTON_SIZE);
        FlatSVGIcon icon = (FlatSVGIcon) a.getValue(Action.LARGE_ICON_KEY);
        a.putValue(Action.LARGE_ICON_KEY, icon.derive(TITLED_TOOLBAR_ICON_SCALE));
        return button;
    }
}
