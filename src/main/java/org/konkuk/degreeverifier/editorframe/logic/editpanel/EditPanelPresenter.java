package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableLectureCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditInnerPannel;

import javax.swing.*;
import java.util.LinkedList;

public class EditPanelPresenter {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final EditInnerPannel panel;

    public EditPanelPresenter(EditInnerPannel editPanel) {
        this.panel = editPanel;

        initEnableLink();
        disableAll();

        if (editorModel.getSelectedDegree() != null) {
            enableAll();
        }
        editorModel.observe(EditorModel.On.DEGREE_SELECTED, selectedDegrees_ -> {
            LinkedList<EditableDegreeCriteria> selectedDegrees = (LinkedList<EditableDegreeCriteria>) selectedDegrees_;
            disableAll();
            if (selectedDegrees.size() == 1) {
                enableDegreeForm();
                updateDegree(selectedDegrees.get(0));
            } else {
                clearDegreeForm();
                clearCriteriaForm();
                clearLectureForm();
                clearRecursiveForm();
            }
        });
        editorModel.observe(EditorModel.On.NODE_SELECTED, unused -> {
            disableAll();
            if (editorModel.getSelectedNodeObjects().size() == 1
                    && !(editorModel.getSelectedNode() instanceof EditableDegreeCriteria)) {
                enableAll();
                updateRecursive();
            } else {
                enableDegreeForm();
                clearCriteriaForm();
                clearLectureForm();
                clearRecursiveForm();
            }
        });
    }

    private void updateDegree(EditableDegreeCriteria degree) {
        panel.degreeNameField.setText(degree.degreeName);
        panel.degreeMinimumCreditSpinner.setValue(degree.minimumCredit);
        panel.degreeDescriptionField.setText(degree.description);
    }

    private void updateRecursive() {
        EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNode();
        panel.setImportantCheckBox.setSelected(recursive.isImportant());
        panel.criteriaDescriptionField.setText(recursive.getDescription());
        if (recursive.lectureCriteria != null) {
            EditableLectureCriteria lecture = recursive.getEditableLectureCriteria();
            panel.useLectureRadioButton.setSelected(true);
            panel.lectureNameField.setText(lecture.lectureName);
            if (lecture.getMinimumSemester() != null) {
                Semester min = lecture.getMinimumSemester();
                panel.useMinimumSemesterCheckBox.setSelected(true);
                panel.minimumSemesterComboBox.setSelectedItem(min);
            } else {
                panel.useMinimumSemesterCheckBox.setSelected(false);
                panel.minimumSemesterComboBox.setSelectedIndex(0);
            }
            if (lecture.getMaximumSemester() != null) {
                Semester max = lecture.getMaximumSemester();
                panel.useMaximumSemesterCheckBox.setSelected(true);
                panel.maximumSemesterComboBox.setSelectedItem(max);
            } else {
                panel.useMaximumSemesterCheckBox.setSelected(false);
                panel.maximumSemesterComboBox.setSelectedIndex(0);
            }
            if (lecture.description != null) {
                panel.lectureDescriptionField.setText(lecture.description);
            } else {
                panel.lectureDescriptionField.setText("");
            }
            panel.setNonExclusiveCheckBox.setSelected(lecture.isNonExclusive());
            clearRecursiveForm();
        } else {
            panel.useRecursiveRadioButton.setSelected(true);
            if (recursive.minimumPass != null) {
                panel.useMinimumPassCheckBox.setSelected(true);
                panel.minimumPassSpinner.setValue(recursive.minimumPass);
            } else {
                panel.useMinimumPassCheckBox.setSelected(false);
                panel.minimumPassSpinner.setValue(0);
            }
            if (recursive.maximumPass != null) {
                panel.useMaximumPassCheckBox.setSelected(true);
                panel.maximumPassSpinner.setValue(recursive.maximumPass);
            } else {
                panel.useMaximumPassCheckBox.setSelected(false);
                panel.maximumPassSpinner.setValue(0);
            }
            clearLectureForm();
        }
    }

    private void clearDegreeForm() {
        panel.degreeNameField.setText("");
        panel.degreeMinimumCreditSpinner.setValue(0);
        panel.degreeDescriptionField.setText("");
    }

    private void clearCriteriaForm() {
        panel.setImportantCheckBox.setSelected(false);
        panel.criteriaDescriptionField.setText("");
    }

    private void clearRecursiveForm() {
        panel.useRecursiveRadioButton.setSelected(false);
        panel.useMinimumPassCheckBox.setSelected(false);
        panel.minimumPassSpinner.setValue(0);
        panel.useMaximumPassCheckBox.setSelected(false);
        panel.maximumPassSpinner.setValue(0);
    }

    private void clearLectureForm() {
        panel.useLectureRadioButton.setSelected(false);
        panel.lectureNameField.setText("");
        panel.useMinimumSemesterCheckBox.setSelected(false);
        panel.minimumSemesterComboBox.setSelectedIndex(0);
        panel.useMaximumSemesterCheckBox.setSelected(false);
        panel.maximumSemesterComboBox.setSelectedIndex(0);
        panel.setNonExclusiveCheckBox.setSelected(false);
        panel.lectureDescriptionField.setText("");
    }

    private void disableAll() {
        panel.degreeNameLabel.setEnabled(false);
        panel.setImportantCheckBox.setEnabled(false);
    }

    private void enableDegreeForm() {
        panel.degreeNameLabel.setEnabled(true);
    }

    private void enableAll() {
        enableDegreeForm();
        panel.setImportantCheckBox.setEnabled(true);
    }

    private void initEnableLink() {
        linkEnable(panel.degreeNameLabel,
                panel.degreeNameField,
                panel.degreeMinimumCreditLabel,
                panel.degreeMinimumCreditSpinner,
                panel.degreeDescriptionLabel,
                panel.degreeDescriptionField
        );

        linkEnable(panel.setImportantCheckBox,
                linkSelectEnable(panel.useRecursiveRadioButton,
                        linkSelectEnable(panel.useMinimumPassCheckBox, panel.minimumPassSpinner),
                        linkSelectEnable(panel.useMaximumPassCheckBox, panel.maximumPassSpinner)
                ),
                linkSelectEnable(panel.useLectureRadioButton,
                        linkSelectEnable(panel.useMinimumSemesterCheckBox, panel.minimumSemesterComboBox),
                        linkSelectEnable(panel.useMaximumSemesterCheckBox, panel.maximumSemesterComboBox),
                        panel.lectureNameField,
                        panel.setNonExclusiveCheckBox,
                        panel.lectureNameLabel,
                        panel.lectureDescriptionLabel,
                        panel.lectureDescriptionField
                ),
                panel.criteriaDescriptionLabel,
                panel.criteriaDescriptionField
        );
    }

    private AbstractButton linkSelectEnable(AbstractButton parent, JComponent... children) {
        parent.addChangeListener(e -> {
            boolean enabled = parent.isEnabled();
            boolean selected = parent.isSelected();

            for (JComponent child : children) {
                child.setEnabled(enabled && selected);
            }
        });
        return parent;
    }

    private JComponent linkEnable(JComponent parent, JComponent... children) {
        parent.addPropertyChangeListener("enabled", e -> {
            boolean enabled = parent.isEnabled();
            for (JComponent child : children) {
                child.setEnabled(enabled);
            }
        });
        return parent;
    }
}
