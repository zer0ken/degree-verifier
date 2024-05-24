package org.konkuk.common;

import org.konkuk.common.verifier.DegreeVerifier;

import java.util.ArrayList;
import java.util.List;

public class DegreeManager {
    private final List<DegreeVerifier> degreeVerifiers;
    private final List<Lecture> lectures;

    public DegreeManager() {
        degreeVerifiers = new ArrayList<>();
        lectures = new ArrayList<>();
    }

    public void loadVerifierFromJsonFile(String filename) {

    }

    public void loadLecturesFromFile(String filename) {

    }
}
