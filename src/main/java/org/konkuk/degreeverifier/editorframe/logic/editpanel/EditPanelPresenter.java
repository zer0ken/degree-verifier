package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
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
                updateDegree(selectedDegrees.get(0));
                enableDegreeForm();
            } else {
                clearDegreeForm();
                clearCriteriaForm();
                clearLectureForm();
                clearRecursiveForm();
            }
        });
        editorModel.observe(EditorModel.On.NODES_SELECTED, unused -> {
            disableAll();
            if (editorModel.getSelectedNodeObjects().size() == 1
                    && !(editorModel.getSelectedNodeObject() instanceof EditableDegreeCriteria)) {
                clearCriteriaForm();
                clearLectureForm();
                clearRecursiveForm();
                updateRecursive();
                enableAll();
            } else {
                clearCriteriaForm();
                clearLectureForm();
                clearRecursiveForm();
                enableDegreeForm();
            }
        });
    }

    private void updateDegree(EditableDegreeCriteria degree) {
        panel.degreeNameField.setText(degree.degreeName);
        panel.degreeVersionSpinner.setValue(degree.version);
        panel.degreeMinimumCreditSpinner.setValue(degree.minimumCredit);
        panel.degreeDescriptionField.setText(degree.description);
        if (degree.getMinimumSemester() != null) {
            Semester min = degree.getMinimumSemester();
            panel.useDegreeMinimumSemesterCheckBox.setSelected(true);
            panel.degreeMinimumSemesterComboBox.setSelectedItem(min);
        } else {
            panel.useDegreeMinimumSemesterCheckBox.setSelected(false);
            panel.degreeMinimumSemesterComboBox.setSelectedItem(0);
        }
        if (degree.getMaximumSemester() != null) {
            Semester max = degree.getMaximumSemester();
            panel.useDegreeMaximumSemesterCheckBox.setSelected(true);
            panel.degreeMaximumSemesterComboBox.setSelectedItem(max);
        } else {
            panel.useDegreeMaximumSemesterCheckBox.setSelected(false);
            panel.degreeMaximumSemesterComboBox.setSelectedItem(0);
        }
    }

    private void updateRecursive() {
        EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
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
            if (recursive.needAllPass != null) {
                panel.setNeedAllPassCheckBox.setSelected(recursive.needsAllPass());
            } else {
                panel.setNeedAllPassCheckBox.setSelected(false);
            }
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
        panel.degreeVersionSpinner.setValue(1);
        panel.degreeMinimumCreditSpinner.setValue(0);
        panel.degreeDescriptionField.setText("");
        panel.useDegreeMaximumSemesterCheckBox.setSelected(false);
        panel.useDegreeMaximumSemesterCheckBox.setSelected(false);
        panel.degreeMinimumSemesterComboBox.setSelectedItem(0);
        panel.degreeMaximumSemesterComboBox.setSelectedItem(0);
    }

    private void clearCriteriaForm() {
        panel.setImportantCheckBox.setSelected(false);
        panel.criteriaDescriptionField.setText("");
    }

    private void clearRecursiveForm() {
        panel.useRecursiveRadioButton.setSelected(false);
        panel.setNeedAllPassCheckBox.setSelected(false);
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
        panel.setNonExclusiveCheckBox.setSelected(LectureCriteria.DEFAULT_NON_EXCLUSIVE);
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
                panel.degreeVersionLabel,
                panel.degreeVersionSpinner,
                panel.degreeMinimumCreditLabel,
                panel.degreeMinimumCreditSpinner,
                panel.degreeDescriptionLabel,
                panel.degreeDescriptionField,
                linkSelectEnable(panel.useDegreeMinimumSemesterCheckBox, panel.degreeMinimumSemesterComboBox),
                linkSelectEnable(panel.useDegreeMaximumSemesterCheckBox, panel.degreeMaximumSemesterComboBox)
        );

        linkEnable(panel.setImportantCheckBox,
                linkSelectEnable(panel.useRecursiveRadioButton,
                        linkSelectEnable(panel.useMinimumPassCheckBox, panel.minimumPassSpinner),
                        linkSelectEnable(panel.useMaximumPassCheckBox, panel.maximumPassSpinner),
                        panel.setNeedAllPassCheckBox
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
