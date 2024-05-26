package org.konkuk.common.snapshot;

import org.konkuk.common.criteria.RecursiveCriteria;

public class RecursiveSnapshot implements Snapshot  {
    public final RecursiveCriteria criteria;
    public final boolean passed;

    public final LectureSnapshot lectureSnapshot;
    public final RecursiveSnapshot[] subSnapshot;

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean passed, RecursiveSnapshot[] subSnapshot) {
        this.criteria = criteria;
        this.passed = passed;
        this.lectureSnapshot = null;
        this.subSnapshot = subSnapshot;
    }

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean passed, LectureSnapshot lectureSnapshot) {
        this.criteria = criteria;
        this.passed = passed;
        this.lectureSnapshot = lectureSnapshot;
        this.subSnapshot = null;
    }
}
