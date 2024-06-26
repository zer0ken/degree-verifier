package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;

public class EditableLectureCriteria extends LectureCriteria implements Editable {
    private final LectureCriteria original;

    public boolean edited = false;

    public EditableLectureCriteria(LectureCriteria toCopy) {
        super(toCopy);

        original = toCopy;
    }

    public EditableLectureCriteria() {
        super(null, "", null, null, null, null, null, null);

        original = null;
    }

    public void updateDescription(String description) {
        this.description = description;
        edited = true;
    }

    public void updateLectureName(String lectureName) {
        this.lectureName = lectureName;
        edited = true;
    }

    public void updateMinimumGrade(String MinimumGrade) {
        this.minimumGrade = minimumGrade;
        edited = true;
    }

    public void updateNonExclusive(Boolean nonExclusive) {
        this.nonExclusive = nonExclusive;
        edited = true;
    }

    public void updateMinimumYearSemester(Integer minimumYear, String minimumSemester) {
        this.minimumYear = minimumYear;
        this.minimumSemester = minimumSemester;
        edited = true;
    }

    public void updateMaximumYearSemester(Integer maximumYear, String maximumSemester) {
        this.maximumYear = maximumYear;
        this.maximumSemester = maximumSemester;
        edited = true;
    }

    @Override
    public boolean isEdited() {
        return edited;
    }

    @Override
    public void rollback() {
    }
}
