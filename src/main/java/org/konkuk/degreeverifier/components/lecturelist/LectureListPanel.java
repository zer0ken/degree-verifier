package org.konkuk.degreeverifier.components.lecturelist;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_LECTURE_LIST_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_LECTURE_LIST_SIZE;

public class LectureListPanel extends JPanel {
    public LectureListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_LECTURE_LIST_SIZE);
        setPreferredSize(PREFERRED_LECTURE_LIST_SIZE);

        JTable table = new LectureTable();

        JScrollPane scrollPane = new JScrollPane(
                table,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(new LectureListToolbar(), BorderLayout.NORTH);
        add(scrollPane);
    }
}
