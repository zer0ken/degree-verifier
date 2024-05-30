package org.konkuk.client.component;

import org.konkuk.client.logic.StudentListModel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Dimensions.MINIMUM_STUDENT_LIST_SIZE;

public class StudentListPanel extends JPanel {
    public StudentListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_STUDENT_LIST_SIZE);

        JList<String> studentList = new JList<>(new StudentListModel());

        JScrollPane scrollPane = new JScrollPane(
                studentList,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new TitlePanel("학생 목록"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
