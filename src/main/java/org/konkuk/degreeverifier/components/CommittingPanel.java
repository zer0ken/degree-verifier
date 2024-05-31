package org.konkuk.degreeverifier.components;

import org.konkuk.degreeverifier.components.committedlist.CommittedDegreePanel;
import org.konkuk.degreeverifier.components.studentlist.StudentListPanel;
import org.konkuk.degreeverifier.components.verifiedlist.VerifiedDegreePanel;

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
        withInformation.setResizeWeight(0.9f);

        add(withInformation);
    }
}
