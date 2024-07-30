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

        tabbedPane.addTab("검토하기", null, new SplitPanel(), "학생을 개별적으로 검토합니다.");
        tabbedPane.addTab("성적표", null, getWrappedTable(new TranscriptTable()), "불러온 성적표를 확인합니다.");
        tabbedPane.addTab("기존 학위", null, getWrappedTable(new EarlyCommitTable()), "불러온 기존 학위를 확인합니다.");
        tabbedPane.addTab("기존 학위 유효성", null, getWrappedTable(new ValidateOldCommitTable()), "기존 학위의 유효성 검사 결과를 확인합니다.");
        tabbedPane.addTab("새로 인정한 학위", null, getWrappedTable(new NewOnlyCommitTable()), "새로 인정한 학위를 확인합니다.");
        tabbedPane.addTab("전체 학위", null, getWrappedTable(new NewAndOldCommitTable()), "기존 학위를 포함해 인정한 모든 학위를 확인합니다.");
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
