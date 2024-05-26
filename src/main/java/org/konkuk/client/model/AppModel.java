package org.konkuk.client.model;

import org.konkuk.common.DegreeManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AppModel {
    private static AppModel instance = null;

    public static AppModel getInstance() {
        return instance != null ? instance : new AppModel();
    }

    private final DegreeManager degreeManager;

    private Thread verifierLoaderThread;
    private Thread lectureLoaderThread;
    private Thread verifierThread;

    public enum Observer {
        ON_START_VERIFIER_LOAD,
        ON_VERIFIER_LOADED,
        ON_START_LECTURE_LOAD,
        ON_LECTURE_LOADED,
        ON_START_VERIFY,
        ON_VERIFIED
    }

    private final List<Runnable> onStartVerifierLoad;
    private final List<Runnable> onVerifierLoaded;
    private final List<Runnable> onStartLectureLoad;
    private final List<Runnable> onLectureLoaded;
    private final List<Runnable> onStartVerify;
    private final List<Runnable> onVerified;

    private AppModel() {
        instance = this;

        degreeManager = new DegreeManager();

        onStartVerifierLoad = new ArrayList<>();
        onVerifierLoaded = new ArrayList<>();
        onStartLectureLoad = new ArrayList<>();
        onLectureLoaded = new ArrayList<>();
        onStartVerify = new ArrayList<>();
        onVerified = new ArrayList<>();
    }

    public void observe(Observer observer, Runnable runnable) {
        if (observer == null) {
            return;
        }

        if (observer == Observer.ON_START_VERIFIER_LOAD) {
            onStartVerifierLoad.add(runnable);
        } else if (observer == Observer.ON_VERIFIER_LOADED) {
            onVerifierLoaded.add(runnable);
        } else if (observer == Observer.ON_START_LECTURE_LOAD) {
            onStartLectureLoad.add(runnable);
        } else if (observer == Observer.ON_LECTURE_LOADED) {
            onLectureLoaded.add(runnable);
        } else if (observer == Observer.ON_START_VERIFY) {
            onStartVerify.add(runnable);
        } else if (observer == Observer.ON_VERIFIED) {
            onVerified.add(runnable);
        }
    }

    private void notify(Observer observer) {
        if (observer == null) {
            return;
        }

        if (observer == Observer.ON_START_VERIFIER_LOAD) {
            onStartVerifierLoad.forEach(Runnable::run);
        } else if (observer == Observer.ON_VERIFIER_LOADED) {
            onVerifierLoaded.forEach(Runnable::run);
        } else if (observer == Observer.ON_START_LECTURE_LOAD) {
            onStartLectureLoad.forEach(Runnable::run);
        } else if (observer == Observer.ON_LECTURE_LOADED) {
            onLectureLoaded.forEach(Runnable::run);
        } else if (observer == Observer.ON_START_VERIFY) {
            onStartVerify.forEach(Runnable::run);
        } else if (observer == Observer.ON_VERIFIED) {
            onVerified.forEach(Runnable::run);
        }
    }

    public void loadVerifiers() {
        if (verifierLoaderThread != null && verifierLoaderThread.isAlive()) {
            verifierLoaderThread.interrupt();
        }
        notify(Observer.ON_START_VERIFIER_LOAD);
        verifierLoaderThread = new Thread(() -> {
            degreeManager.loadAllVerifier("C:\\Users\\a3759\\IdeaProjects\\degree-verifier\\src\\test\\resources\\org\\konkuk\\common\\verifier");
            notify(Observer.ON_VERIFIER_LOADED);
        });
        verifierLoaderThread.start();
    }

    public void loadLectures() {
        if (lectureLoaderThread != null && lectureLoaderThread.isAlive()) {
            lectureLoaderThread.interrupt();
        }
        notify(Observer.ON_START_LECTURE_LOAD);
        lectureLoaderThread = new Thread(() -> {
            degreeManager.loadLectures("C:\\Users\\a3759\\IdeaProjects\\degree-verifier\\src\\test\\resources\\org\\konkuk\\common\\LecturesExample2.tsv");
            notify(Observer.ON_LECTURE_LOADED);
        });
        lectureLoaderThread.start();
    }

    public void verify() {
        if (verifierThread != null && verifierThread.isAlive()) {
            verifierThread.interrupt();
        }
        notify(Observer.ON_START_VERIFY);
        verifierThread = new Thread(() -> {
            degreeManager.verify();
            notify(Observer.ON_VERIFIED);
        });
        verifierThread.start();
    }

    public DegreeManager getDegreeManager() {
        return degreeManager;
    }
}
