package org.konkuk.degreeverifier.components.statusbar;

import org.konkuk.degreeverifier.components.statusbar.progress.ProgressStatusPanel;

import javax.swing.*;

public class StatusPanel extends JToolBar {
    public StatusPanel() {
        add(Box.createGlue());
        add(new ProgressStatusPanel());
    }
}
