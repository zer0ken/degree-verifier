package org.konkuk.client.components.statusbar.progress;

import org.konkuk.client.logic.ProgressStatusModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.konkuk.client.ui.Strings.NO_TASK;

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
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
