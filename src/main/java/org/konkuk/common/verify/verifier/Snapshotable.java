package org.konkuk.common.verify.verifier;

import org.konkuk.common.verify.snapshot.Snapshot;

public interface Snapshotable {
    Snapshot takeSnapshot();
}
