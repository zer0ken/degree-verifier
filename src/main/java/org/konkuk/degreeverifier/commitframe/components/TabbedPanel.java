package org.konkuk.degreeverifier.commitframe.components;

import org.konkuk.degreeverifier.commitframe.components.tables.*;
import org.konkuk.degreeverifier.common.components.ScrollPaneWrapper;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.SEPARATOR_TABBED_TABLE;

public class TabbedPanel extends JPanel {
    public TabbedPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("검토하기", new SplitPanel());
        tabbedPane.addTab("성적표", getWrappedTable(new TranscriptTable()));
        tabbedPane.addTab("기존 학위", getWrappedTable(new EarlyCommitTable()));
        tabbedPane.addTab("기존 학위 유효성", getWrappedTable(new ValidateOldCommitTable()));
        tabbedPane.addTab("새로 인정한 학위", getWrappedTable(new NewOnlyCommitTable()));
        tabbedPane.addTab("전체 학위", getWrappedTable(new NewAndOldCommitTable()));
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        add(tabbedPane);
    }

    private JPanel getWrappedTable(JTable table) {
        JPanel wrapped = new JPanel(new BorderLayout());
        wrapped.setBorder(SEPARATOR_TABBED_TABLE);
        wrapped.add(ScrollPaneWrapper.wrapTable(table));
        return wrapped;
    }
}
