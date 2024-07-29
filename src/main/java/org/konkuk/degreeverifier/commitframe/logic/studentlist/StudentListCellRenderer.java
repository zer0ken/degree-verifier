package org.konkuk.degreeverifier.commitframe.logic.studentlist;

import org.konkuk.degreeverifier.business.student.Student;
import org.konkuk.degreeverifier.ui.Colors;

import javax.swing.*;
import java.awt.*;

public class StudentListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Student student = (Student) value;
        if (!student.getCommittedDegrees().isEmpty()) {
            component.setForeground(Colors.COMMON_GREEN);
            return component;
        }

        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
