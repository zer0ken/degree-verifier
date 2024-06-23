package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;

public class EditableDegreeCriteria extends DegreeCriteria implements Editable {
    public EditableDegreeCriteria(DegreeCriteria toCopy) {
        super(toCopy);
        recursiveCriteria = new EditableRecursiveCriteria(recursiveCriteria);
        original = toCopy;
        added = false;
        removed = false;
        edited = false;
    }

    public EditableDegreeCriteria(String degreeName) {
        super(degreeName, null, null, new EditableRecursiveCriteria());

        original = null;
        added = true;
        removed = false;
        edited = true;
    }

    private final DegreeCriteria original;

    public boolean added;
    public boolean removed;
    public boolean edited;

    public EditableRecursiveCriteria getEditableRecursiveCriteria() {
        return (EditableRecursiveCriteria) recursiveCriteria;
    }

    public void rollback() {
        this.description = original.description;
        this.degreeName = original.degreeName;
        this.minimumCredit = original.minimumCredit;
        this.recursiveCriteria = new EditableRecursiveCriteria(original.recursiveCriteria);
        removed = false;
    }

    public void updateDescription(String description) {
        this.description = description;
        edited = true;
    }

    public void updateDegreeName(String degreeName) {
        this.degreeName = degreeName;
        edited = true;
    }

    public void updateMinimumCredit(Integer minimumCredit) {
        this.minimumCredit = minimumCredit;
        edited = true;
    }

    public DegreeCriteria getOriginal() {
        return original;
    }
}
