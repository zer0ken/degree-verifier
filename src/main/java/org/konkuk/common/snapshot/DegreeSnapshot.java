package org.konkuk.common.snapshot;

import org.konkuk.common.criteria.DegreeCriteria;

public class DegreeSnapshot implements Snapshot {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final RecursiveSnapshot recursiveSnapshot;

    public DegreeSnapshot(DegreeCriteria criteria, boolean verified, RecursiveSnapshot recursiveSnapshot) {
        this.criteria = criteria;
        this.verified = verified;
        this.recursiveSnapshot = recursiveSnapshot;
    }
}
