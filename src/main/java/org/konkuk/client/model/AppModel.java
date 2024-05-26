package org.konkuk.client.model;

import org.konkuk.common.DegreeManager;

public class AppModel {
    private static AppModel instance = null;

    public static AppModel getInstance() {
        return instance != null ? instance : new AppModel();
    }

    private final DegreeManager degreeManager;

    private AppModel() {
        instance = this;

        degreeManager = new DegreeManager();
        degreeManager.loadLectures("C:\\Users\\a3759\\IdeaProjects\\degree-verifier\\src\\test\\resources\\org\\konkuk\\common\\LecturesExample2.tsv");
        degreeManager.loadAllVerifier("C:\\Users\\a3759\\IdeaProjects\\degree-verifier\\src\\test\\resources\\org\\konkuk\\common\\verifier");
        degreeManager.verify();
    }

    public DegreeManager getDegreeManager() {
        return degreeManager;
    }
}
