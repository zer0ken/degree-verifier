package org.konkuk.degreeverifier.editorframe.logic.editpanel;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.common.logic.SimplifiedDocumentListener;
import org.konkuk.degreeverifier.editorframe.components.editpanel.EditInnerPannel;

public class EditPanelListener {
    private final EditorModel editorModel = EditorModel.getInstance();
    private final EditInnerPannel panel;

    public EditPanelListener(EditInnerPannel panel) {
        this.panel = panel;

        panel.degreeNameField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.degreeNameField.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            String text = panel.degreeNameField.getText();
            if (!degree.degreeName.equals(text)) {
                degree.updateDegreeName(text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.degreeDescriptionField.getDocument().addDocumentListener(new SimplifiedDocumentListener(e -> {
            if (!panel.degreeDescriptionField.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            String text = panel.degreeDescriptionField.getText();
            if (!degree.description.equals(text)) {
                degree.updateDescription(text.isEmpty() ? null : text);
                editorModel.notifyUpdatedSelectedDegree();
            }
        }));

        panel.degreeMinimumCreditSpinner.addChangeListener(e -> {
            if (!panel.degreeMinimumCreditSpinner.isEnabled()) {
                return;
            }
            EditableDegreeCriteria degree = editorModel.getSelectedDegree();
            Integer value = (Integer) panel.degreeMinimumCreditSpinner.getValue();
            if (!degree.minimumCredit.equals(value)) {
                degree.updateMinimumCredit(value);
                editorModel.notifyUpdatedSelectedDegree();
            }
        });
    }
}
