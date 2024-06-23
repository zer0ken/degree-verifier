package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;

public class EditableRecursiveCriteria extends RecursiveCriteria implements Editable {
    private final RecursiveCriteria original;
    public boolean added = false;
    public boolean removed = false;
    public boolean edited = false;

    public EditableRecursiveCriteria(RecursiveCriteria toCopy) {
        super(toCopy);
        if (lectureCriteria != null) {
            lectureCriteria = new EditableLectureCriteria(lectureCriteria);
        }
        if (subcriteria != null) {
            for (int i = 0; i < subcriteria.length; i++) {
                subcriteria[i] = new EditableRecursiveCriteria(subcriteria[i]);
            }
        }

        original = toCopy;
        added = false;
        removed = false;
        edited = false;
    }

    public EditableRecursiveCriteria() {
        super(null, null, new EditableLectureCriteria(), null, null, null, null);

        original = null;
        added = true;
        removed = false;
        edited = true;
    }

    public EditableLectureCriteria getEditableLectureCriteria() {
        return (EditableLectureCriteria) lectureCriteria;
    }

    public void rollback() {
        description = original.description;
        important = original.important;
        lectureCriteria = original.lectureCriteria;
        needAllPass = original.needAllPass;
        minimumPass = original.minimumPass;
        maximumPass = original.maximumPass;
        subcriteria = original.subcriteria;

        if (lectureCriteria != null) {
            lectureCriteria = new EditableLectureCriteria(lectureCriteria);
        }
        if (subcriteria != null) {
            for (int i = 0; i < subcriteria.length; i++) {
                subcriteria[i] = new EditableRecursiveCriteria(subcriteria[i]);
            }
        }

        removed = false;
    }

    public void updateDescription(String description) {
        this.description = description;
        edited = true;
    }

    public void updateImportant(Boolean important) {
        this.important = important;
        edited = true;
    }

    public void updateNeedAllPass(Boolean needAllPass) {
        this.needAllPass = needAllPass;
        edited = true;
    }

    public void updateMinimumPass(Integer minimumPass) {
        this.minimumPass = minimumPass;
        edited = true;
    }

    public void updateMaximumPass(Integer maximumPass) {
        this.maximumPass = maximumPass;
        edited = true;
    }
}
