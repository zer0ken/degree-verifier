package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    public StatusPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("<작업 이름> 진행중...");
        JProgressBar progressBar = new JProgressBar();

        add(label);
        add(progressBar);
    }
}
