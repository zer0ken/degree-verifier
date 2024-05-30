package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Dimensions.MINIMUM_LECTURE_LIST_SIZE;

public class LectureListPanel extends JPanel {
    public LectureListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_LECTURE_LIST_SIZE);

        JTree tree = new JTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel("교과목 성적"), BorderLayout.NORTH);
        add(scrollPane);
    }
}
