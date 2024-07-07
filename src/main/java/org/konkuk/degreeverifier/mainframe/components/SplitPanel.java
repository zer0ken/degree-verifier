package org.konkuk.degreeverifier.mainframe.components;

import org.konkuk.degreeverifier.mainframe.components.committedlist.CommittedDegreePanel;
import org.konkuk.degreeverifier.mainframe.components.informationtree.InformationPanel;
import org.konkuk.degreeverifier.mainframe.components.lecturelist.LectureListPanel;
import org.konkuk.degreeverifier.mainframe.components.studentlist.StudentListPanel;
import org.konkuk.degreeverifier.mainframe.components.verifiedlist.VerifiedDegreePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_EVERY_SIDE;


public class SplitPanel extends JPanel {
    public SplitPanel() {
        setLayout(new BorderLayout());

        JPanel verifiedDegreePanel = new VerifiedDegreePanel();
        JPanel committedDegreePanel = new CommittedDegreePanel();
        JPanel lectureListPanel = new LectureListPanel();

        verifiedDegreePanel.setBorder(new CompoundBorder(SEPARATOR_EVERY_SIDE, verifiedDegreePanel.getBorder()));
        committedDegreePanel.setBorder(new CompoundBorder(SEPARATOR_EVERY_SIDE, committedDegreePanel.getBorder()));
        lectureListPanel.setBorder(new CompoundBorder(SEPARATOR_EVERY_SIDE, lectureListPanel.getBorder()));

        JSplitPane commitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                verifiedDegreePanel,
                committedDegreePanel
        );
        commitPane.setResizeWeight(0.5d);

        JSplitPane withLectureList = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                commitPane,
                lectureListPanel
        );
        withLectureList.setResizeWeight(1);

        JSplitPane withInformation = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                withLectureList,
                new InformationPanel()
        );
        withInformation.setResizeWeight(1);

        JSplitPane totalPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new StudentListPanel(),
                withInformation
        );

        add(totalPane);
    }
}
