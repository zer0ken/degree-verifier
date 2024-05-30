package org.konkuk.common.verify.snapshot;


import org.konkuk.common.student.LectureData;
import org.konkuk.common.verify.criteria.LectureCriteria;
import org.konkuk.common.verify.verifier.Creditizable;

public class LectureSnapshot implements Snapshot, Creditizable {
    public final LectureCriteria criteria;
    public final LectureData matched;
    public final boolean verified;

    public LectureSnapshot(LectureCriteria criteria, LectureData matched, boolean verified) {
        this.criteria = criteria;
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
}