package org.konkuk.degreeverifier.business.verify.editable;

import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;

public class EditableLectureCriteria extends LectureCriteria implements Editable {
    private final LectureCriteria original;

    public boolean added = false;
    public boolean removed = false;
    public boolean edited = false;

    public EditableLectureCriteria(LectureCriteria toCopy) {
        super(toCopy);

        original = toCopy;
        added = false;
        removed = false;
        edited = false;
    }

    public EditableLectureCriteria() {
        super(null, null, null, null, null, null, null, null);

        original = null;
        added = true;
        removed = false;
        edited = true;
    }

    public void rollback() {
        description = original.description;
        lectureName = original.lectureName;
        minimumGrade = original.minimumGrade;
        nonExclusive = original.nonExclusive;
        minimumGrade = original.minimumGrade;
        maximumYear = original.maximumYear;
        minimumSemester = original.minimumSemester;
        maximumSemester = original.maximumSemester;
        edited = false;
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
}
