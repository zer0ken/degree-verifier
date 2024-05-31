package org.konkuk.degreeverifier.business.verify.verifier;

import org.konkuk.degreeverifier.business.verify.snapshot.Snapshot;

public interface Snapshotable {
    Snapshot takeSnapshot();
}
