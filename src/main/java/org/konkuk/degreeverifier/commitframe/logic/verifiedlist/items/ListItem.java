package org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

public abstract class ListItem {
    private final DegreeSnapshot degreeSnapshot;

    public ListItem(DegreeSnapshot degreeSnapshot) {
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
