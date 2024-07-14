package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;
import org.konkuk.degreeverifier.common.logic.SimplifiedDocumentListener;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditInnerPannel;

import java.util.Objects;

public class EditPanelListener {
    private final EditorModel editorModel = EditorModel.getInstance();

    public EditPanelListener(EditInnerPannel panel) {
        panel.degreeNameField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.degreeNameField.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            String text = panel.degreeNameField.getText().trim();
            if (!text.equals(degree.degreeName) && !editorModel.containsDegree(degree)) {
                degree.updateDegreeName(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.degreeVersionSpinner.addChangeListener(e -> {
            if (!panel.degreeVersionSpinner.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            Integer value = (Integer) panel.degreeVersionSpinner.getValue();
            if (!value.equals(degree.version)) {
                degree.updateVersion(value);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.degreeDescriptionField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.degreeDescriptionField.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            String text = panel.degreeDescriptionField.getText();
            text = text.isEmpty() ? null : text;
            if (!Objects.equals(text, degree.description)) {
                degree.updateDescription(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.degreeMinimumCreditSpinner.addChangeListener(e -> {
            if (!panel.degreeMinimumCreditSpinner.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            Integer value = (Integer) panel.degreeMinimumCreditSpinner.getValue();
            if (!value.equals(degree.minimumCredit)) {
                degree.updateMinimumCredit(value);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.useDegreeMinimumSemesterCheckBox.addChangeListener(e -> {
            if (!panel.useDegreeMinimumSemesterCheckBox.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            if (degree == null) {
                return;
            }
            Semester semester = (Semester) panel.degreeMinimumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            boolean selected = panel.useDegreeMinimumSemesterCheckBox.isSelected();
            if (selected) {
                degree.updateMinimumYearSemester(semester.year, semester.semester.value);
            } else {
                degree.updateMinimumYearSemester(null, null);
            }
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.useDegreeMaximumSemesterCheckBox.addChangeListener(e -> {
            if (!panel.useDegreeMaximumSemesterCheckBox.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            if (degree == null) {
                return;
            }
            Semester semester = (Semester) panel.degreeMaximumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            boolean selected = panel.useDegreeMaximumSemesterCheckBox.isSelected();
            if (selected) {
                degree.updateMaximumYearSemester(semester.year, semester.semester.value);
            } else {
                degree.updateMaximumYearSemester(null, null);
            }
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.degreeMinimumSemesterComboBox.addActionListener(e -> {
            if (!panel.degreeMinimumSemesterComboBox.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            if (degree == null) {
                return;
            }
            Semester semester = (Semester) panel.degreeMinimumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            degree.updateMinimumYearSemester(semester.year, semester.semester.value);
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.degreeMaximumSemesterComboBox.addActionListener(e -> {
            if (!panel.degreeMaximumSemesterComboBox.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            if (degree == null) {
                return;
            }
            Semester semester = (Semester) panel.degreeMaximumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            degree.updateMaximumYearSemester(semester.year, semester.semester.value);
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.setImportantCheckBox.addChangeListener(e -> {
            if (!panel.setImportantCheckBox.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            boolean selected = panel.setImportantCheckBox.isSelected();
            if (recursive.isImportant() != selected) {
                recursive.updateImportant(selected);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.criteriaDescriptionField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.criteriaDescriptionField.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            String text = panel.criteriaDescriptionField.getText();
            text = text.isEmpty() ? null : text;
            if (!Objects.equals(text, recursive.description)) {
                recursive.updateDescription(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.useRecursiveRadioButton.addChangeListener(e -> {
            if (!panel.useRecursiveRadioButton.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            boolean selected = panel.useRecursiveRadioButton.isSelected();
            if (recursive.lectureCriteria != null && selected) {
                recursive.switchToRecursiveCriteria();
                editorModel.notifyUpdatedSelectedDegree();
            } else if (recursive.lectureCriteria == null && !selected) {
                recursive.switchToLectureCriteria();
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.setNeedAllPassCheckBox.addChangeListener(e -> {
            if (!panel.setNeedAllPassCheckBox.isEnabled()) {
                return;
            }
            boolean selected = panel.setNeedAllPassCheckBox.isSelected();
            if (selected) {
                panel.useMinimumPassCheckBox.setSelected(false);
                panel.useMaximumPassCheckBox.setSelected(false);
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            if (selected != recursive.needsAllPass()) {
                recursive.updateNeedAllPass(selected);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.useMinimumPassCheckBox.addChangeListener(e -> {
            if (!panel.useMinimumPassCheckBox.isEnabled()) {
                return;
            }
            boolean selected = panel.useMinimumPassCheckBox.isSelected();
            if (selected) {
                panel.setNeedAllPassCheckBox.setSelected(false);
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            if (selected) {
                Integer value = (Integer) panel.minimumPassSpinner.getValue();
                if (!value.equals(recursive.minimumPass)) {
                    recursive.updateMinimumPass(value);
                    editorModel.notifyUpdatedSelectedDegree();
                }
            } else if (!Objects.equals(null, recursive.minimumPass)) {
                recursive.updateMinimumPass(null);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.useMaximumPassCheckBox.addChangeListener(e -> {
            if (!panel.useMaximumPassCheckBox.isEnabled()) {
                return;
            }
            boolean selected = panel.useMaximumPassCheckBox.isSelected();
            if (selected) {
                panel.setNeedAllPassCheckBox.setSelected(false);
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            if (selected) {
                Integer value = (Integer) panel.maximumPassSpinner.getValue();
                if (!value.equals(recursive.maximumPass)) {
                    recursive.updateMaximumPass(value);
                    editorModel.notifyUpdatedSelectedDegree();
                }
            } else {
                if (!Objects.equals(null, recursive.maximumPass)) {
                    recursive.updateMaximumPass(null);
                    editorModel.notifyUpdatedSelectedDegree();
                }
            }
        });

        panel.minimumPassSpinner.addChangeListener(e -> {
            if (!panel.minimumPassSpinner.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            Integer value = (Integer) panel.minimumPassSpinner.getValue();
            if (!value.equals(recursive.minimumPass)) {
                recursive.updateMinimumPass(value);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.maximumPassSpinner.addChangeListener(e -> {
            if (!panel.maximumPassSpinner.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null) {
                return;
            }
            Integer value = (Integer) panel.maximumPassSpinner.getValue();
            if (!value.equals(recursive.maximumPass)) {
                recursive.updateMaximumPass(value);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });

        panel.lectureNameField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.lectureNameField.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            String text = panel.lectureNameField.getText();
            text = text.isEmpty() ? null : text;
            if (!Objects.equals(text, recursive.description)) {
                recursive.getEditableLectureCriteria().updateLectureName(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.lectureNameField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.lectureNameField.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            String text = panel.lectureNameField.getText();
            text = text.isEmpty() ? null : text;
            if (!Objects.equals(text, recursive.lectureCriteria.lectureName)) {
                recursive.getEditableLectureCriteria().updateLectureName(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.lectureDescriptionField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.lectureDescriptionField.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            String text = panel.lectureDescriptionField.getText();
            text = text.isEmpty() ? null : text;
            if (!Objects.equals(text, recursive.lectureCriteria.description)) {
                recursive.getEditableLectureCriteria().updateDescription(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.useMinimumSemesterCheckBox.addChangeListener(e -> {
            if (!panel.useMinimumSemesterCheckBox.isEnabled()) {
                return;
            }
            boolean selected = panel.useMinimumSemesterCheckBox.isSelected();
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            Semester semester = (Semester) panel.minimumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            if (selected) {
                recursive.getEditableLectureCriteria().updateMinimumYearSemester(semester.year, semester.semester.value);
            } else {
                recursive.getEditableLectureCriteria().updateMinimumYearSemester(null, null);
            }
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.useMaximumSemesterCheckBox.addChangeListener(e -> {
            if (!panel.useMaximumSemesterCheckBox.isEnabled()) {
                return;
            }
            boolean selected = panel.useMaximumSemesterCheckBox.isSelected();
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            Semester semester = (Semester) panel.maximumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            if (selected) {
                recursive.getEditableLectureCriteria().updateMaximumYearSemester(semester.year, semester.semester.value);
            } else {
                recursive.getEditableLectureCriteria().updateMaximumYearSemester(null, null);
            }
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.minimumSemesterComboBox.addActionListener(e -> {
            if (!panel.minimumSemesterComboBox.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            Semester semester = (Semester) panel.minimumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            recursive.getEditableLectureCriteria().updateMinimumYearSemester(semester.year, semester.semester.value);
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.maximumSemesterComboBox.addActionListener(e -> {
            if (!panel.maximumSemesterComboBox.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            Semester semester = (Semester) panel.maximumSemesterComboBox.getSelectedItem();
            if (semester == null) {
                return;
            }
            recursive.getEditableLectureCriteria().updateMaximumYearSemester(semester.year, semester.semester.value);
            editorModel.notifyUpdatedSelectedDegree();
        });

        panel.setNonExclusiveCheckBox.addActionListener(e -> {
            if(!panel.setNonExclusiveCheckBox.isEnabled()) {
                return;
            }
            EditableRecursiveCriteria recursive = (EditableRecursiveCriteria) editorModel.getSelectedNodeObject();
            if (recursive == null || recursive.getEditableLectureCriteria() == null) {
                return;
            }
            boolean selected = panel.setNonExclusiveCheckBox.isSelected();
            if (selected != recursive.getEditableLectureCriteria().isNonExclusive()) {
                recursive.getEditableLectureCriteria().updateNonExclusive(selected);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });
    }
}
