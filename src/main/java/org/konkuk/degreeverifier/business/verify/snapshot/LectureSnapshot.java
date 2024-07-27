package org.konkuk.degreeverifier.business.verify.snapshot;


import org.konkuk.degreeverifier.business.student.LectureData;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;

import java.util.Objects;

public class LectureSnapshot implements Snapshot {
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

    public LectureSnapshot(String lectureName, Integer lectureCredit) {
        criteria = null;
        matched = new LectureData(null, null, null, null, lectureName, null, lectureCredit, null);
        verified = true;
        degreeName = null;
        duplicatedDegrees = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LectureSnapshot)) {
            return false;
        }
        LectureSnapshot o = (LectureSnapshot) obj;
        return Objects.equals(matched.name, o.matched.name);
    }

    public Integer creditize() {
        if (verified) {
            return matched.credit;
        }
        return null;
    }

    @Override
    public String toString() {
        return criteria != null ? criteria.lectureName : matched != null ? matched.name : "";
    }
}