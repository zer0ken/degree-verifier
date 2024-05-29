package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class StudentListPanel extends JPanel {
    public StudentListPanel() {
        setLayout(new BorderLayout());

        JTree tree = new JTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel("학생 목록"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
