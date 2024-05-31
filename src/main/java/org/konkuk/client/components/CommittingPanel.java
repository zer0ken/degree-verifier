package org.konkuk.client.components;

import org.konkuk.client.components.studentlist.StudentListPanel;
import org.konkuk.client.components.verifiedlist.VerifiedDegreePanel;

import javax.swing.*;
import java.awt.*;


public class CommittingPanel extends JPanel {
    public CommittingPanel() {
        setLayout(new BorderLayout());

        JSplitPane commitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new VerifiedDegreePanel(),
                new CommittedDegreePanel()
        );

        JSplitPane withLectureList = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                commitPane,
                new LectureListPanel()
        );

        JSplitPane interactivePane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new StudentListPanel(),
                withLectureList
        );

        JSplitPane withInformation = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                interactivePane,
                new InformationPanel()
        );

        add(withInformation);
    }
}
