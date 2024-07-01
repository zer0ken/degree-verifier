package org.konkuk.degreeverifier.business.verify.snapshot;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

import java.util.LinkedHashSet;
import java.util.Set;

public class DegreeSnapshot implements Snapshot, Creditizable {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final RecursiveSnapshot recursiveSnapshot;
    public final Set<String> sufficientDegrees = new LinkedHashSet<>();
    public final Set<String> insufficientDegrees = new LinkedHashSet<>();

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
        return criteria.toString();
    }
}
