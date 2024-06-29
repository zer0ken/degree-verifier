package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;

public class EditableDegreeCriteria extends DegreeCriteria implements Editable {
    public EditableDegreeCriteria(DegreeCriteria toCopy) {
        super(toCopy);
        recursiveCriteria = new EditableRecursiveCriteria(recursiveCriteria);
        original = toCopy;
        removed = false;
        edited = false;
        getEditableRecursiveCriteria().isRoot = true;
    }

    public EditableDegreeCriteria(String degreeName) {
        super(degreeName, null, 0, new EditableRecursiveCriteria());
        original = null;
        removed = false;
        edited = false;
        getEditableRecursiveCriteria().isRoot = true;
    }

    private final DegreeCriteria original;

    public boolean removed;
    public boolean edited;

    public EditableRecursiveCriteria getEditableRecursiveCriteria() {
        return (EditableRecursiveCriteria) recursiveCriteria;
    }

    @Override
    public boolean isEdited() {
        return edited;
    }

    @Override
    public void rollback() {
        if (original != null) {
            this.description = original.description;
            this.degreeName = original.degreeName;
            this.minimumCredit = original.minimumCredit;
        }
        edited = false;
    }

    public void rollbackRecursively() {
        rollback();
        getEditableRecursiveCriteria().rollbackRecursively();
    }

    public DegreeCriteria upcast() {
        if (!edited && !getEditableRecursiveCriteria().hasChangedChildren()) {
            return null;
        }
        DegreeCriteria upcasted = new DegreeCriteria(this);
        upcasted.recursiveCriteria = getEditableRecursiveCriteria().upcast();
        return upcasted;
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
