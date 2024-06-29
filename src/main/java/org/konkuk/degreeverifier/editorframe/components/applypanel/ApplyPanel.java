package org.konkuk.degreeverifier.editorframe.components.applypanel;

import org.konkuk.degreeverifier.editorframe.actions.ApplyChangesAction;
import org.konkuk.degreeverifier.editorframe.actions.CancelChangesAction;
import org.konkuk.degreeverifier.editorframe.actions.ConfirmChangesAction;
import org.konkuk.degreeverifier.editorframe.actions.RollbackAllChangesAction;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Borders.APPLY_PANEL_BORDER;

public class ApplyPanel extends JPanel {
    public ApplyPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(APPLY_PANEL_BORDER);
        add(new JButton(new ApplyChangesAction()));
        add(new JButton(new RollbackAllChangesAction()));
        add(Box.createHorizontalGlue());
        add(new JButton(new ConfirmChangesAction()));
        add(new JButton(new CancelChangesAction()));
    }
}
