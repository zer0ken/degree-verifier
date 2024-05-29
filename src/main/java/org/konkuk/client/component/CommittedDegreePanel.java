package org.konkuk.client.component;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class CommittedDegreePanel extends JPanel {
    public CommittedDegreePanel() {
        setLayout(new BorderLayout());

        JTree tree = new JTree();

        JScrollPane scrollPane = new JScrollPane(
                tree,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel("인정된 학위 목록"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
