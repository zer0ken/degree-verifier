package org.konkuk.degreeverifier.components;

import org.konkuk.degreeverifier.components.committedlist.CommittedDegreePanel;
import org.konkuk.degreeverifier.components.informationtree.InformationPanel;
import org.konkuk.degreeverifier.components.lecturelist.LectureListPanel;
import org.konkuk.degreeverifier.components.studentlist.StudentListPanel;
import org.konkuk.degreeverifier.components.verifiedlist.VerifiedDegreePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_EVERY_SIDE;
import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_WITHOUT_BOTTOM;


public class SplitPanel extends JPanel {
    public SplitPanel() {
        setLayout(new BorderLayout());

        JPanel verifiedDegreePanel = new VerifiedDegreePanel();
        JPanel committedDegreePanel = new CommittedDegreePanel();
        JPanel lectureListPanel = new LectureListPanel();

        verifiedDegreePanel.setBorder(new CompoundBorder(SEPARATOR_EVERY_SIDE, verifiedDegreePanel.getBorder()));
        committedDegreePanel.setBorder(new CompoundBorder(SEPARATOR_EVERY_SIDE, committedDegreePanel.getBorder()));
        lectureListPanel.setBorder(new CompoundBorder(SEPARATOR_WITHOUT_BOTTOM, lectureListPanel.getBorder()));

        JSplitPane commitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                verifiedDegreePanel,
                committedDegreePanel
        );
        commitPane.setResizeWeight(0.5f);

        JSplitPane withLectureList = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                commitPane,
                lectureListPanel
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
