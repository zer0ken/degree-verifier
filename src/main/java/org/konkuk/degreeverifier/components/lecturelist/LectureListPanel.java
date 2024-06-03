package org.konkuk.degreeverifier.components.lecturelist;

import org.konkuk.degreeverifier.logic.lecturelist.LectureTableModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_LECTURE_LIST_SIZE;

public class LectureListPanel extends JPanel {
    public LectureListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_LECTURE_LIST_SIZE);

        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new LectureTableModel());
        table.setComponentPopupMenu(new LectureListPopupMenu());

        JScrollPane scrollPane = new JScrollPane(
                table,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new LectureListToolbar(), BorderLayout.NORTH);
        add(scrollPane);
    }
}
