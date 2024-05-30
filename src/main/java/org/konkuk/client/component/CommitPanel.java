package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;


public class CommitPanel extends JPanel {
    public CommitPanel() {
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new StudentListPanel(),
                new StudentPanel()
        );

        add(splitPane);
    }
}
