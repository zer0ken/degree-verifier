package org.konkuk.degreeverifier.editorframe.components.applypanel;

import org.konkuk.degreeverifier.editorframe.actions.ApplyChangesAction;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.APPLY_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Strings.CANCEL_EDIT;
import static org.konkuk.degreeverifier.ui.Strings.CONFIRM_EDIT;

public class ApplyPanel extends JPanel {
    public ApplyPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBorder(APPLY_PANEL_BORDER);
        add(new JButton(new ApplyChangesAction()));
        add(new JButton(CONFIRM_EDIT));
        add(new JButton(CANCEL_EDIT));
    }
}
