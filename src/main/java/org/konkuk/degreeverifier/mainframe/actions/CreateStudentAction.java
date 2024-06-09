package org.konkuk.degreeverifier.mainframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.business.DefaultPaths.STUDENTS_PATH;
import static org.konkuk.degreeverifier.ui.Colors.COMMON_GRAY;
import static org.konkuk.degreeverifier.ui.Strings.*;

public class CreateStudentAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public CreateStudentAction() {
        putValue(NAME, CREATE_STUDENT);
        putValue(SHORT_DESCRIPTION, CREATE_STUDENT);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/add_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String directoryName = getStudentDirectoryName();
        if (directoryName == null) {
            return;
        }
        appModel.addStudent(directoryName);
    }

    private String getStudentDirectoryName() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(CREATE_STUDENT_MESSAGE), constraints);
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        panel.add(new JLabel(STUDENT_ID), constraints);

        constraints.gridy = 2;
        JTextField idField = new JTextField();
        panel.add(idField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(new JLabel(STUDENT_NAME), constraints);

        constraints.gridy = 2;
        JTextField nameField = new JTextField();
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.weightx = 0;
        JLabel info = new JLabel("<html>&nbsp;<br>&nbsp;</html>");
        info.setForeground(COMMON_GRAY);
        panel.add(info, constraints);

        Runnable setInfo = () -> {
            if (idField.getText().isEmpty()) {
                info.setText(STUDENT_ID_IS_EMPTY);
            } else if (nameField.getText().isEmpty()) {
                info.setText(STUDENT_NAME_IS_EMPTY);
            } else if (nameField.getText().matches(".*[\\\\\\/:*?\"<>|-].*")) {
                info.setText(STUDENT_NAME_IS_INVALID);
            } else {
                info.setText(STUDENT_ID_IS_VALID);
            }
        };
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setInfo.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setInfo.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setInfo.run();
            }
        };
        idField.getDocument().addDocumentListener(listener);
        nameField.getDocument().addDocumentListener(listener);

        int result = JOptionPane.showConfirmDialog(null, panel, CREATE_STUDENT, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return STUDENTS_PATH + "\\" + idField.getText() + " - " + nameField.getText();
        }
        return null;
    }
}
