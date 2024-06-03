package org.konkuk.degreeverifier.business.verify.snapshot;


import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

public class LectureSnapshot implements Snapshot, Creditizable {
    public final LectureCriteria criteria;
    public final LectureData matched;
    public final boolean verified;

    public final String degreeName;
    public final String[] duplicatedDegrees;

    public LectureSnapshot(LectureCriteria criteria, LectureData matched, boolean verified,
                           String degreeName, String[] duplicatedDegrees) {
        this.criteria = new LectureCriteria(criteria);
        this.matched = matched;
        this.verified = verified;
        this.degreeName = degreeName;
        this.duplicatedDegrees = duplicatedDegrees;
    }

    @Override
    public int creditize() {
        if (verified) {
            return matched.credit;
        }
        return 0;
    }

    @Override
    public String toString() {
        return criteria.lectureName;
    }
}