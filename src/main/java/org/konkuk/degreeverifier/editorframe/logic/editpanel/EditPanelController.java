package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditPanel;

import java.util.Calendar;

public class EditPanelController {
    private final EditPanel editPanel;

    public EditPanelController(EditPanel editPanel) {
        this.editPanel = editPanel;
    }

    private void linkEnable() {
        Semester s = new Semester(2022, Semester.Type.FIRST);
        while (s.year <= Calendar.getInstance().get(Calendar.YEAR) + 4) {
            editPanel.minimumSemesterComboBox.addItem(s);
            editPanel.maximumSemesterComboBox.addItem(s);
            s = s.next();
        }
        editPanel.useLectureRadioButton.addChangeListener(
                e -> {
                    editPanel.lectureNameField.setEnabled(editPanel.useLectureRadioButton.isSelected());
                    editPanel.useMinimumSemesterCheckBox.setEnabled(editPanel.useLectureRadioButton.isSelected());
                    editPanel.useMaximumSemesterCheckBox.setEnabled(editPanel.useLectureRadioButton.isSelected());
                    editPanel.setNonExclusiveCheckBox.setEnabled(editPanel.useLectureRadioButton.isSelected());
                }
        );
        editPanel.useMinimumSemesterCheckBox.addChangeListener(
                e -> editPanel.minimumSemesterComboBox.setEnabled(editPanel.useMinimumSemesterCheckBox.isSelected())
        );
        editPanel.useMaximumSemesterCheckBox.addChangeListener(
                e -> editPanel.maximumSemesterComboBox.setEnabled(editPanel.useMaximumSemesterCheckBox.isSelected())
        );

    }
}
