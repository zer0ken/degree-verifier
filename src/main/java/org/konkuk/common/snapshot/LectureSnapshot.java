package org.konkuk.common.snapshot;


import org.konkuk.common.LectureData;
import org.konkuk.common.criteria.LectureCriteria;

public class LectureSnapshot implements Snapshot {
    public final LectureCriteria criteria;
    public final LectureData matched;
    public final boolean passed;

    public LectureSnapshot(LectureCriteria criteria, LectureData matched, boolean passed) {
        this.criteria = criteria;
        this.matched = matched;
        this.passed = passed;
    }
}
