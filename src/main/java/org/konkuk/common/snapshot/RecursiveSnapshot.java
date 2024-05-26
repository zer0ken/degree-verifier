package org.konkuk.common.snapshot;

import org.konkuk.common.criteria.RecursiveCriteria;

public class RecursiveSnapshot implements Snapshot  {
    public final RecursiveCriteria criteria;
    public final boolean verified;

    public final LectureSnapshot lectureSnapshot;
    public final RecursiveSnapshot[] subSnapshot;

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, RecursiveSnapshot[] subSnapshot) {
        this.criteria = criteria;
        this.verified = verified;
        this.lectureSnapshot = null;
        this.subSnapshot = subSnapshot;
    }

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, LectureSnapshot lectureSnapshot) {
        this.criteria = criteria;
        this.verified = verified;
        this.lectureSnapshot = lectureSnapshot;
        this.subSnapshot = null;
    }
}
