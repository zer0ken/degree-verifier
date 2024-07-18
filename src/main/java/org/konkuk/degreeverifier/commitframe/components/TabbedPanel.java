package org.konkuk.degreeverifier.commitframe.components;

import org.konkuk.degreeverifier.commitframe.components.tables.CommitTable;
import org.konkuk.degreeverifier.commitframe.components.tables.EarlyCommitTable;
import org.konkuk.degreeverifier.commitframe.components.tables.TranscriptTable;
import org.konkuk.degreeverifier.common.components.ScrollPaneWrapper;

import javax.swing.*;
import java.awt.*;

public class TabbedPanel extends JPanel {
    public TabbedPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("검토하기", new SplitPanel());
        tabbedPane.addTab("성적표", ScrollPaneWrapper.wrapTable(new TranscriptTable()));
        tabbedPane.addTab("기존 학위", ScrollPaneWrapper.wrapTable(new EarlyCommitTable()));
        tabbedPane.addTab("인정한 학위", ScrollPaneWrapper.wrapTable(new CommitTable()));
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        add(tabbedPane);
    }
}
