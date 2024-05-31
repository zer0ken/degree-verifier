package org.konkuk.degreeverifier.components.statusbar.progress;

import org.konkuk.degreeverifier.components._popupmenus.TitledGridBagPopupMenu;
import org.konkuk.degreeverifier.logic.ProgressTracker;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Dimensions.PROGRESS_GRID_INSETS;
import static org.konkuk.degreeverifier.ui.Strings.NO_TASK;

public class DetailedProgressPopupMenu extends TitledGridBagPopupMenu {
    public DetailedProgressPopupMenu() {
        setTitle(NO_TASK);

        constraints.insets = PROGRESS_GRID_INSETS;

        constraints.gridwidth = 2;
        add(titleLabel, constraints);
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
}
