package org.konkuk.degreeverifier.business.verify.snapshot;


import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

public class LectureSnapshot implements Snapshot, Creditizable {
    public final LectureCriteria criteria;
    public final LectureData matched;
    public final boolean verified;

    public LectureSnapshot(LectureCriteria criteria, LectureData matched, boolean verified) {
        this.criteria = new LectureCriteria(criteria);
        this.matched = matched;
        this.verified = verified;
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