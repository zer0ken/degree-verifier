package org.konkuk.common.snapshot;


import org.konkuk.common.verifier.LectureVerifier;

public class LectureSnapshot extends LectureVerifier implements Snapshot {
    protected LectureSnapshot(LectureVerifier toCopy) {
        super(toCopy);
    }
}
