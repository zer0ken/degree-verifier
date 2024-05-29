package org.konkuk.client;

import org.konkuk.common.DegreeManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private static AppModel instance = null;
    private final DegreeManager degreeManager;
    private final java.util.List<Runnable> onStartVerifierLoad;
    private final java.util.List<Runnable> onVerifierLoaded;
    private final java.util.List<Runnable> onStartLectureLoad;
    private final java.util.List<Runnable> onLectureLoaded;
    private final java.util.List<Runnable> onStartVerify;
    private final List<Runnable> onVerified;
    private Thread verifierLoaderThread;
    private Thread lectureLoaderThread;
    private Thread verifierThread;
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

    public static AppModel getInstance() {
        return instance != null ? instance : new AppModel();
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

        SwingUtilities.invokeLater(() -> {
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
        });
    }

    public void loadVerifiers() {
        if (verifierLoaderThread != null && verifierLoaderThread.isAlive()) {
            verifierLoaderThread.interrupt();
        }
        notify(Observer.ON_START_VERIFIER_LOAD);
        verifierLoaderThread = new Thread(() -> {
            degreeManager.loadAllVerifier("./src/test/resources/org/konkuk/common/verifier");
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
            degreeManager.loadLectures("./src/main/resources/LecturesExample.tsv");
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

    public enum Observer {
        ON_START_VERIFIER_LOAD,
        ON_VERIFIER_LOADED,
        ON_START_LECTURE_LOAD,
        ON_LECTURE_LOADED,
        ON_START_VERIFY,
        ON_VERIFIED
    }
}
