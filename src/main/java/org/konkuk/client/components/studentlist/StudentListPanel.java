package org.konkuk.client.components.studentlist;

import org.konkuk.client.logic.studentlist.StudentListEnable;
import org.konkuk.client.logic.studentlist.StudentListModel;
import org.konkuk.client.logic.studentlist.StudentListSelectionListener;
import org.konkuk.common.student.Student;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Dimensions.MINIMUM_STUDENT_LIST_SIZE;

public class StudentListPanel extends JPanel {
    public StudentListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_STUDENT_LIST_SIZE);

        JList<Student> studentList = new JList<>(new StudentListModel());
        studentList.setEnabled(false);
        studentList.setComponentPopupMenu(new StudentListPopupMenu());
        studentList.addListSelectionListener(new StudentListSelectionListener());

        JScrollPane scrollPane = new JScrollPane(
                studentList,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new StudentToolbar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        new StudentListEnable(studentList);
    }
}
