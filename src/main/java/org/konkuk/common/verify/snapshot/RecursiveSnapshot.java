package org.konkuk.common.verify.snapshot;

import org.konkuk.common.verify.criteria.RecursiveCriteria;
import org.konkuk.common.verify.verifier.Creditizable;

public class RecursiveSnapshot implements Snapshot, Creditizable {
    public final RecursiveCriteria criteria;
    public final boolean verified;

    public final LectureSnapshot lectureSnapshot;
    public final RecursiveSnapshot[] subSnapshots;

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, RecursiveSnapshot[] subSnapshots) {
        this.criteria = criteria;
        this.verified = verified;
        this.lectureSnapshot = null;
        this.subSnapshots = subSnapshots;
    }

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, LectureSnapshot lectureSnapshot) {
        this.criteria = criteria;
        this.verified = verified;
        this.lectureSnapshot = lectureSnapshot;
        this.subSnapshots = null;
    }

    @Override
    public int creditize() {
        if(!verified) {
            return 0;
        }
        if (lectureSnapshot != null) {
            return lectureSnapshot.creditize();
        } else if(subSnapshots != null) {
            int sum = 0;
            for (RecursiveSnapshot subSnapshot : subSnapshots) {
                sum += subSnapshot.creditize();
            }
            return sum;
        }
        return 0;
    }
}
