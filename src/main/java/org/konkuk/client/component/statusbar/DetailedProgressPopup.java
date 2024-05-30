package org.konkuk.client.component.statusbar;

import org.konkuk.client.logic.ProgressTracker;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.client.ui.Dimensions.PROGRESS_GRID_INSETS;
import static org.konkuk.client.ui.Strings.NO_TASK;

public class DetailedProgressPopup extends JPopupMenu {
    private final GridBagConstraints constraints;

    private final JLabel innerLabel;

    public DetailedProgressPopup() {
        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.insets = PROGRESS_GRID_INSETS;

        innerLabel = new JLabel(NO_TASK);
        constraints.gridwidth = 2;
        add(innerLabel, constraints);
        constraints.gridwidth = 1;
    }

    public Runnable add(ProgressTracker tracker) {
        JLabel labelComponent = new JLabel(tracker.message);
        JProgressBar progressBarComponent = new JProgressBar(tracker);

        constraints.gridx = 0;
        add(labelComponent, constraints);
        constraints.gridx = 1;
        add(progressBarComponent, constraints);
        return () -> SwingUtilities.invokeLater(() -> {
            remove(labelComponent);
            remove(progressBarComponent);
            pack();
        });
    }

    public JLabel getInnerLabel() {
        return innerLabel;
    }
}
