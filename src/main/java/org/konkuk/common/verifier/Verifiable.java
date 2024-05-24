package org.konkuk.common.verifier;

import org.konkuk.common.Lecture;

import java.util.List;

public interface Verifiable {
    List<LectureVerifier> match(List<Lecture> lectures);
    boolean verify();
    void clear();
}
