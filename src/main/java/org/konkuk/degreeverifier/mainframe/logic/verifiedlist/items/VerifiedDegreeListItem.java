package org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

public abstract class VerifiedDegreeListItem {
    private final DegreeSnapshot degreeSnapshot;

    public VerifiedDegreeListItem(DegreeSnapshot degreeSnapshot) {
        this.degreeSnapshot = degreeSnapshot;
    }

    public DegreeSnapshot getDegreeSnapshot() {
        return degreeSnapshot;
    }

    @Override
    public String toString() {
        return getDegreeSnapshot() != null ? getDegreeSnapshot().toString() : null;
    }
}
