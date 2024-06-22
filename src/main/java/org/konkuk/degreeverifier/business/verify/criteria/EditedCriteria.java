package org.konkuk.degreeverifier.business.verify.criteria;

public class EditedCriteria extends RecursiveCriteria {
    public boolean added = false;
    public boolean removed = false;
    public boolean edited = false;

    private RecursiveCriteria after = new RecursiveCriteria(this);

    public EditedCriteria(RecursiveCriteria toCopy) {
        super(toCopy);
    }

    public void rollback() {
        removed = false;
        after = new RecursiveCriteria(this);
    }

    public void updateDescription(String description) {
        after = new RecursiveCriteria(
                description,
                after.important,
                after.lectureCriteria,
                after.needAllPass,
                after.minimumPass,
                after.maximumPass,
                after.subcriteria
        );
    }

    public void updateImportant(Boolean important) {
        after = new RecursiveCriteria(
                after.description,
                important,
                after.lectureCriteria,
                after.needAllPass,
                after.minimumPass,
                after.maximumPass,
                after.subcriteria
        );
    }

    public void updateLectureCriteria(LectureCriteria lectureCriteria) {
        after = new RecursiveCriteria(
                after.description,
                after.important,
                lectureCriteria,
                after.needAllPass,
                after.minimumPass,
                after.maximumPass,
                after.subcriteria
        );
    }

    public void updateNeedAllPass(Boolean needAllPass) {
        after = new RecursiveCriteria(
                after.description,
                after.important,
                after.lectureCriteria,
                needAllPass,
                after.minimumPass,
                after.maximumPass,
                after.subcriteria
        );
    }

    public void updateMinimumPass(Integer minimumPass) {
        after = new RecursiveCriteria(
                after.description,
                after.important,
                after.lectureCriteria,
                after.needAllPass,
                minimumPass,
                after.maximumPass,
                after.subcriteria
        );
    }

    public void updateMaximumPass(Integer maximumPass) {
        after = new RecursiveCriteria(
                after.description,
                after.important,
                after.lectureCriteria,
                after.needAllPass,
                after.minimumPass,
                maximumPass,
                after.subcriteria
        );
    }

    public void updateSubcriteria(RecursiveCriteria[] subcriteria) {
        after = new RecursiveCriteria(
                after.description,
                after.important,
                after.lectureCriteria,
                after.needAllPass,
                after.minimumPass,
                after.maximumPass,
                subcriteria
        );
    }
}
