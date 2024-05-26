package org.konkuk.common.snapshot;

import org.konkuk.common.criteria.DegreeCriteria;

public class DegreeSnapshot implements Snapshot {
    public final DegreeCriteria criteria;
    public final boolean passed;
    public final RecursiveSnapshot recursiveSnapshot;

    public DegreeSnapshot(DegreeCriteria criteria, boolean passed, RecursiveSnapshot recursiveSnapshot) {
        this.criteria = criteria;
        this.passed = passed;
        this.recursiveSnapshot = recursiveSnapshot;
    }
}
