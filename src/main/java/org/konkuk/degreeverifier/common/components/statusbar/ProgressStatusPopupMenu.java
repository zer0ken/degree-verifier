package org.konkuk.degreeverifier.common.components.statusbar;

import org.konkuk.degreeverifier.common.logic.statusbar.ProgressStatusPopupMenuModel;
import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.PROGRESS_GRID_INSETS;
import static org.konkuk.degreeverifier.ui.Strings.NO_TASK;

public class ProgressStatusPopupMenu extends JPopupMenu {
    private final JLabel titleLabel = new JLabel();
    private final GridBagConstraints constraints = new GridBagConstraints();

    public ProgressStatusPopupMenu() {
        this(NO_TASK);
    }

    public ProgressStatusPopupMenu(String title) {
        setLayout(new GridBagLayout());

        setText(title);

        constraints.insets = PROGRESS_GRID_INSETS;

        constraints.gridwidth = 2;
        add(titleLabel, constraints);
        constraints.gridwidth = 1;

        new ProgressStatusPopupMenuModel(this);
    }

    public void setText(String text) {
        titleLabel.setText(text);
    }

    public void add(ProgressTracker tracker) {
        JLabel labelComponent = new JLabel(tracker.message);
        JProgressBar progressBarComponent = new JProgressBar(tracker);

        constraints.gridx = 0;
        add(labelComponent, constraints);
        constraints.gridx = 1;
        add(progressBarComponent, constraints);

        tracker.addFinishListener(() -> {
            Point oldLocation = getLocation();
            Dimension oldSize = getPreferredSize();
            remove(labelComponent);
            remove(progressBarComponent);
            revalidate();
            pack();
            repaint();
        });
    }

    @Override
    public void show(Component invoker, int x, int y) {
        super.show(invoker, 0, - getPreferredSize().height);
    }
}
