package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JPanel {
    public StudentPanel() {
        setLayout(new BorderLayout());

        JSplitPane commitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new CommittedDegreePanel(),
                new VerifiedDegreePanel()
        );
        commitPane.setDividerLocation(200);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                commitPane,
                new LectureListPanel()
        );
        splitPane.setResizeWeight(0.7f);

        add(splitPane);
    }
}
