package org.konkuk.degreeverifier.commitframe.components;

import org.konkuk.degreeverifier.commitframe.components.tables.CommitTable;
import org.konkuk.degreeverifier.commitframe.components.tables.EarlyCommitTable;
import org.konkuk.degreeverifier.commitframe.components.tables.TranscriptTable;
import org.konkuk.degreeverifier.common.components.ScrollPaneWrapper;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_TABBED_TABLE;

public class TabbedPanel extends JPanel {
    public TabbedPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel inner1 = new JPanel(new BorderLayout());
        inner1.setBorder(SEPARATOR_TABBED_TABLE);
        inner1.add(ScrollPaneWrapper.wrapTable(new TranscriptTable()));

        JPanel inner2 = new JPanel(new BorderLayout());
        inner2.setBorder(SEPARATOR_TABBED_TABLE);
        inner2.add(ScrollPaneWrapper.wrapTable(new EarlyCommitTable()));

        JPanel inner3 = new JPanel(new BorderLayout());
        inner3.setBorder(SEPARATOR_TABBED_TABLE);
        inner3.add(ScrollPaneWrapper.wrapTable(new CommitTable()));

        tabbedPane.addTab("검토하기", new SplitPanel());
        tabbedPane.addTab("성적표", inner1);
        tabbedPane.addTab("기존 학위", inner2);
        tabbedPane.addTab("인정한 학위", inner3);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        add(tabbedPane);
    }
}
