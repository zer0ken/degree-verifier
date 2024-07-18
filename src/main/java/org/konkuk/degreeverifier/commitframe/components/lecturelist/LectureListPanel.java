package org.konkuk.degreeverifier.commitframe.components.lecturelist;

import org.konkuk.degreeverifier.common.components.ScrollPaneWrapper;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_LECTURE_LIST_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_LECTURE_LIST_SIZE;

public class LectureListPanel extends JPanel {
    public LectureListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_LECTURE_LIST_SIZE);
        setPreferredSize(PREFERRED_LECTURE_LIST_SIZE);

        JTable table = new LectureTable();

        add(new LectureListToolbar(), BorderLayout.NORTH);
        add(ScrollPaneWrapper.wrapTable(table));
    }
}
