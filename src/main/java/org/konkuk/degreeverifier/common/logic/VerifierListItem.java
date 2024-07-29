package org.konkuk.degreeverifier.common.logic;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

public abstract class VerifierListItem {
    private DegreeVerifier degreeVerifier = null;
    private DegreeSnapshot snapshot = null;

    public VerifierListItem() {
    }

    public VerifierListItem(DegreeVerifier degreeVerifier) {
        this.degreeVerifier = degreeVerifier;
    }

    public VerifierListItem(DegreeSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    public DegreeVerifier getDegreeVerifier() {
        return degreeVerifier;
    }

    public DegreeSnapshot getSnapshot() {
        if (snapshot == null && degreeVerifier != null) {
            snapshot = degreeVerifier.optimize();
        }
        return snapshot;
    }

    @Override
    public String toString() {
        return degreeVerifier != null ? degreeVerifier.toString() : snapshot != null ? snapshot.toString() : "";
    }
}
