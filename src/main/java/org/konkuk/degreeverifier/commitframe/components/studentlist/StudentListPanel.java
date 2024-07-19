package org.konkuk.degreeverifier.commitframe.components.studentlist;

import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.commitframe.logic.studentlist.*;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_STUDENT_LIST_SIZE;

public class StudentListPanel extends JPanel {
    public StudentListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_STUDENT_LIST_SIZE);
        setPreferredSize(MINIMUM_STUDENT_LIST_SIZE);

        JList<Student> list = new JList<>(new StudentListModel());
        list.setComponentPopupMenu(new StudentListPopupMenu());
        list.addListSelectionListener(new StudentListSelectionListener());
        list.setBackground(UIManager.getColor("RootPane.background"));
        list.addMouseListener(new StudentListMouseAdapter());
        list.setSelectionModel(new StudentListSelectionModel());
        list.setCellRenderer(new StudentListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(
                list,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(new StudentToolbar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
