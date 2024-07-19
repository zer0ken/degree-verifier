package org.konkuk.degreeverifier.common.components.statusbar;

import org.konkuk.degreeverifier.common.logic.statusbar.ProgressStatusPanelModel;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Borders.PROGRESS_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.PROGRESS_BAR_LEFT_GAP;
import static org.konkuk.degreeverifier.ui.Strings.NO_TASK;

public class ProgressStatusPanel extends JPanel {
    private final JLabel label;
    private final JProgressBar progressBar;

    public ProgressStatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(PROGRESS_PANEL_BORDER);
        setAlignmentY(CENTER_ALIGNMENT);

        label = new JLabel(NO_TASK);
        progressBar = new JProgressBar();
//        ProgressStatusPopupMenu popupMenu = new ProgressStatusPopupMenu();
//        setComponentPopupMenu(popupMenu);

        add(label);
        add(Box.createHorizontalStrut(PROGRESS_BAR_LEFT_GAP));
        add(progressBar);

//        addMouseListener(new ShowPopupMenuOnClick());

        new ProgressStatusPanelModel(this);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void setModel(BoundedRangeModel tracker) {
        progressBar.setModel(tracker);
    }
}
