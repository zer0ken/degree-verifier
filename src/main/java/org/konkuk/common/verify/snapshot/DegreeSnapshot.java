package org.konkuk.common.verify.snapshot;

import org.konkuk.common.verify.criteria.DegreeCriteria;
import org.konkuk.common.verify.verifier.Creditizable;

public class DegreeSnapshot implements Snapshot, Creditizable {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final RecursiveSnapshot recursiveSnapshot;

    public DegreeSnapshot(DegreeCriteria criteria, boolean verified, RecursiveSnapshot recursiveSnapshot) {
        this.criteria = criteria;
        this.verified = verified;
        this.recursiveSnapshot = recursiveSnapshot;
    }

    @Override
    public int creditize() {
        return 0;
    }
}
