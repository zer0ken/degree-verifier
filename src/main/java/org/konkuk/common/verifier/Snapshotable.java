package org.konkuk.common.verifier;

import org.konkuk.common.snapshot.Snapshot;

public interface Snapshotable {
    Snapshot takeSnapshot();
}
