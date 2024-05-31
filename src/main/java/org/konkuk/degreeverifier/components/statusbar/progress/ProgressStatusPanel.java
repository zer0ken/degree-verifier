package org.konkuk.degreeverifier.components.statusbar.progress;

import org.konkuk.degreeverifier.logic.ProgressStatusModel;
import org.konkuk.degreeverifier.logic.statusbar.ProgressStatusMouseListener;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Strings.NO_TASK;

public class ProgressStatusPanel extends JPanel {
    private final JLabel label;
    private final JProgressBar progressBar;

    private final DetailedProgressPopupMenu popup;

    public ProgressStatusPanel() {
        setLayout(new FlowLayout());

        label = new JLabel(NO_TASK);
        progressBar = new JProgressBar();
        popup = new DetailedProgressPopupMenu();

        progressBar.setVisible(false);
        setComponentPopupMenu(popup);

        add(label);
        add(progressBar);

        new ProgressStatusModel(this);

        addMouseListener(new ProgressStatusMouseListener());
    }

    public JLabel getLabel() {
        return label;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public DetailedProgressPopupMenu getPopup() {
        return popup;
    }
}
