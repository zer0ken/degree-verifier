package org.konkuk.degreeverifier.components.menubar.studentnavigator;

import org.konkuk.degreeverifier.actions.ExportCommitAction;
import org.konkuk.degreeverifier.actions.StartCommitAction;
import org.konkuk.degreeverifier.components.SizedToolbar;

import javax.swing.*;
import java.awt.*;

public class StudentNavigator extends JPanel {
    public StudentNavigator() {
        JButton studentSelectButton = new JButton("이현령 학생 검토중");
        studentSelectButton.setPreferredSize(new Dimension(300, 40));

        JToolBar toolBar = new SizedToolbar(new Dimension(40, 40), 0.7f);
        toolBar.add(new StartCommitAction());
        toolBar.add(new ExportCommitAction());

        add(studentSelectButton);
        add(toolBar);
    }
}
