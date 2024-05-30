package org.konkuk.client.component.statusbar;

import org.konkuk.client.logic.ProgressStatusModel;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.konkuk.client.ui.Dimensions.*;
import static org.konkuk.client.ui.Strings.NO_TASK;

public class ProgressStatusPanel extends JPanel {
    private final JLabel label;
    private final JProgressBar progressBar;

    private final DetailedProgressPopup popup;

    public ProgressStatusPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = PROGRESS_GRID_INSETS;

        label = new JLabel(NO_TASK);
        progressBar = new JProgressBar();
        popup = new DetailedProgressPopup();

        label.setPreferredSize(MINIMUM_PROGRESS_LABEL_SIZE);
        progressBar.setVisible(false);
        setComponentPopupMenu(popup);

        constraints.gridx = 0;
        add(label, constraints);

        constraints.gridx = 1;
        add(progressBar);

        new ProgressStatusModel(this);

        JPanel here = this;

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

    public DetailedProgressPopup getPopup() {
        return popup;
    }
}
