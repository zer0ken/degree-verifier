package org.konkuk.degreeverifier.components;

import org.konkuk.degreeverifier.components.committedlist.CommittedDegreePanel;
import org.konkuk.degreeverifier.components.informationtree.InformationPanel;
import org.konkuk.degreeverifier.components.lecturelist.LectureListPanel;
import org.konkuk.degreeverifier.components.studentlist.StudentListPanel;
import org.konkuk.degreeverifier.components.verifiedlist.VerifiedDegreePanel;

import javax.swing.*;
import java.awt.*;


public class SplitPanel extends JPanel {
    public SplitPanel() {
        setLayout(new BorderLayout());

        JSplitPane commitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new VerifiedDegreePanel(),
                new CommittedDegreePanel()
        );
        commitPane.setResizeWeight(0.5f);

        JSplitPane withLectureList = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                commitPane,
                new LectureListPanel()
        );

        JSplitPane withInformation = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                withLectureList,
                new InformationPanel()
        );
        withInformation.setResizeWeight(0.65f);

        JSplitPane totalPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new StudentListPanel(),
                withInformation
        );
        totalPane.setResizeWeight(0.1f);

        add(totalPane);
    }
}
