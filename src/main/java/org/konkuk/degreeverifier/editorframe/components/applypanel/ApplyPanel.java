package org.konkuk.degreeverifier.editorframe.components.applypanel;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.APPLY_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Strings.APPLY_EDIT;
import static org.konkuk.degreeverifier.ui.Strings.CANCEL_EDIT;

public class ApplyPanel extends JPanel {
    public ApplyPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(APPLY_PANEL_BORDER);
        add(new JButton(CANCEL_EDIT));
        add(new JButton(APPLY_EDIT));
    }
}
