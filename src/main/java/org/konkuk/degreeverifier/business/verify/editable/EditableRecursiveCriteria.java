package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;

import java.util.Arrays;
import java.util.Objects;

public class EditableRecursiveCriteria extends RecursiveCriteria implements Editable {
    private final RecursiveCriteria original;
    public boolean removed;
    public boolean edited;
    public boolean isRoot = false;
    public EditableRecursiveCriteria parent = null;

    public EditableRecursiveCriteria(RecursiveCriteria toCopy) {
        super(toCopy);
        if (lectureCriteria != null) {
            lectureCriteria = new EditableLectureCriteria(lectureCriteria);
        }
        if (subcriteria != null) {
            subcriteria = Arrays.copyOf(subcriteria, subcriteria.length);
            for (int i = 0; i < subcriteria.length; i++) {
                subcriteria[i] = new EditableRecursiveCriteria(subcriteria[i]);
                ((EditableRecursiveCriteria) subcriteria[i]).parent = this;
            }
        }

        original = toCopy;
        removed = false;
        edited = false;
    }

    public EditableRecursiveCriteria() {
        super(null, null, null, null, null, null, new RecursiveCriteria[]{});

        original = null;
        removed = false;
        edited = true;
    }

    public EditableLectureCriteria getEditableLectureCriteria() {
        return (EditableLectureCriteria) lectureCriteria;
    }

    @Override
    public boolean isEdited() {
        return edited;
    }

    @Override
    public void rollback() {
        if (original != null) {
            removed = false;
            description = original.description;
            important = original.important;
            lectureCriteria = original.lectureCriteria;
            needAllPass = original.needAllPass;
            minimumPass = original.minimumPass;
            maximumPass = original.maximumPass;
            if (lectureCriteria != null) {
                lectureCriteria = new EditableLectureCriteria(lectureCriteria);
            }
        } else if (!isRoot) {
            removed = true;
            parent.removeSubcriteria(this);
        }
        edited = false;
    }

    public void rollbackRecursively() {
        rollback();
        if (lectureCriteria != null){
            getEditableLectureCriteria().rollback();
        }else {
            for (RecursiveCriteria _criteria : subcriteria) {
                EditableRecursiveCriteria criteria = (EditableRecursiveCriteria) _criteria;
                criteria.rollbackRecursively();
            }
        }
    }

    public void updateDescription(String description) {
        this.description = description;
        edited = true;
    }

    public void updateImportant(Boolean important) {
        this.important = important ? true : null;
        edited = true;
    }

    public void updateNeedAllPass(Boolean needAllPass) {
        this.needAllPass = needAllPass ? needAllPass : null;
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

    public void switchToLectureCriteria() {
        subcriteria = null;
        maximumPass = null;
        minimumPass = null;
        needAllPass = null;
        if (original == null || original.lectureCriteria == null) {
            lectureCriteria = new EditableLectureCriteria();
        } else {
            lectureCriteria = new EditableLectureCriteria(original.lectureCriteria);
        }
        edited = true;
    }

    public void switchToRecursiveCriteria() {
        lectureCriteria = null;
        if (original == null || original.subcriteria == null) {
            subcriteria = new RecursiveCriteria[]{};
        } else {
            subcriteria = Arrays.copyOf(original.subcriteria, original.subcriteria.length);
            for (int i = 0; i < subcriteria.length; i++) {
                subcriteria[i] = new EditableRecursiveCriteria(subcriteria[i]);
            }
            maximumPass = original.maximumPass;
            minimumPass = original.minimumPass;
            needAllPass = original.needAllPass;
        }
        edited = true;
    }

    public void createSubcriteria() {
        RecursiveCriteria[] old = subcriteria;
        subcriteria = new RecursiveCriteria[subcriteria.length + 1];
        subcriteria[subcriteria.length - 1] = new EditableRecursiveCriteria();
        ((EditableRecursiveCriteria) subcriteria[subcriteria.length - 1]).parent = this;
        if (old.length > 0) System.arraycopy(old, 0, subcriteria, 0, old.length);
        edited = true;
    }

    public void removeSubcriteria(RecursiveCriteria removedSubcriteria) {
        int i;
        for (i = 0; i < subcriteria.length; i++) {
            if (subcriteria[i] == removedSubcriteria) {
                subcriteria[i] = null;
            }
        }
        subcriteria = Arrays.stream(subcriteria).filter(Objects::nonNull).toArray(RecursiveCriteria[]::new);
        edited = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (description != null) {
            sb.append(description).append(" | ");
        }
        if (isImportant()) {
            sb.append("필수 ");
        }
        if (lectureCriteria != null) {
            sb.append("교과목: ").append(lectureCriteria.lectureName);
        } else {
            sb.append("검사 그룹(").append(Arrays.stream(subcriteria).filter(c -> !((EditableRecursiveCriteria) c).removed).count()).append(")");
        }
        return sb.toString();
    }

    public boolean hasChangedChildren() {
        if (edited) {
            return true;
        }
        if (lectureCriteria != null) {
            return getEditableLectureCriteria().edited;
        } else {
            return Arrays.stream(subcriteria)
                    .anyMatch(c -> ((EditableRecursiveCriteria) c).edited);
        }
    }

    public RecursiveCriteria upcast() {
        RecursiveCriteria upcasted = new RecursiveCriteria(this);
        if (lectureCriteria != null) {
            upcasted.lectureCriteria = new LectureCriteria(lectureCriteria);
        } else {
            upcasted.subcriteria = Arrays.stream(subcriteria)
                    .filter(c -> !((EditableRecursiveCriteria)c).removed)
                    .map(c -> ((EditableRecursiveCriteria) c).upcast())
                    .toArray(RecursiveCriteria[]::new);
        }
        return upcasted;
    }
}
