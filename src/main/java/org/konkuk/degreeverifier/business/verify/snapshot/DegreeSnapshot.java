package org.konkuk.degreeverifier.business.verify.snapshot;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

public class DegreeSnapshot implements Snapshot, Creditizable {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final RecursiveSnapshot recursiveSnapshot;

    public DegreeSnapshot(DegreeCriteria criteria, boolean verified, RecursiveSnapshot recursiveSnapshot) {
        this.criteria = new DegreeCriteria(criteria);
        this.verified = verified;
        this.recursiveSnapshot = recursiveSnapshot;
    }

    @Override
    public int creditize() {
        return 0;
    }

    @Override
    public String toString() {
        return criteria.degreeName;
    }
}
