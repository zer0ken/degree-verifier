package org.konkuk.client.components.statusbar;

import org.konkuk.client.components.statusbar.progress.ProgressStatusPanel;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    public StatusPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(new ProgressStatusPanel());
    }
}
