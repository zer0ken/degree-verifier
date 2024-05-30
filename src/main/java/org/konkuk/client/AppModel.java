package org.konkuk.client;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.student.Student;
import org.konkuk.common.verify.Verifier;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.konkuk.client.ui.Strings.STUDENTS_LOADING_MESSAGE;
import static org.konkuk.client.ui.Strings.VERIFIER_LOADING_MESSAGE;
import static org.konkuk.common.DefaultPaths.STUDENTS_PATH;

public class AppModel {
    private static AppModel instance = null;
    private final Map<ObserveOf, List<Runnable>> runnableObserverMap;
    private final Map<ObserveOf, List<Consumer>> consumerObserverMap;
    private final ExecutorService executorService;
    private final Verifier verifier;
    private final List<ProgressTracker> trackers;
    private final List<Student> students;

    private AppModel() {
        instance = this;

        verifier = new Verifier();
        runnableObserverMap = Collections.synchronizedMap(new HashMap<>());
        consumerObserverMap = Collections.synchronizedMap(new HashMap<>());

        executorService = Executors.newCachedThreadPool();
        trackers = Collections.synchronizedList(new LinkedList<>());
        students = Collections.synchronizedList(new LinkedList<>());
    }

    public static AppModel getInstance() {
        return instance != null ? instance : new AppModel();
    }

    public void observe(ObserveOf observeOf, Runnable callback) {
        if (observeOf == null) {
            return;
        }
        if (!runnableObserverMap.containsKey(observeOf)) {
            runnableObserverMap.put(observeOf, new LinkedList<>());
        }
        runnableObserverMap.get(observeOf).add(callback);
    }

    public void observe(ObserveOf observeOf, Consumer<Object> callback) {
        if (observeOf == null) {
            return;
        }
        if (!consumerObserverMap.containsKey(observeOf)) {
            consumerObserverMap.put(observeOf, new LinkedList<>());
        }
        consumerObserverMap.get(observeOf).add(callback);
    }

    private void notify(ObserveOf observeOf) {
        if (observeOf == null) {
            return;
        }
        List<Runnable> observers = runnableObserverMap.get(observeOf);
        if (observers != null) {
            observers.forEach(SwingUtilities::invokeLater);
        }
        if (observeOf.parent != null) {
            notify(observeOf.parent);
        }
    }

    private void notify(ObserveOf observeOf, Object o) {
        if (observeOf == null) {
            return;
        }
        List<Consumer> observers = consumerObserverMap.get(observeOf);
        if (observers != null) {
            observers.forEach(observer -> SwingUtilities.invokeLater(() -> observer.accept(o)));
        }
        if (observeOf.parent != null) {
            notify(observeOf.parent, o);
        }
    }

    private ProgressTracker getTracker(String message) {
        ProgressTracker tracker = new ProgressTracker(message);
        trackers.add(tracker);
        tracker.addOnFinishedListener(() -> trackers.remove(tracker));
        return tracker;
    }

    public void loadVerifiers() {
        submitTask(
                ObserveOf.ON_START_VERIFIER_LOAD,
                ObserveOf.ON_VERIFIER_LOADED,
                VERIFIER_LOADING_MESSAGE,
                verifier::loadAllVerifiers
        );
    }

    public void loadStudents() {
        submitTask(
                ObserveOf.ON_START_STUDENT_LOAD,
                ObserveOf.ON_STUDENT_LOADED,
                STUDENTS_LOADING_MESSAGE,
                (tracker) -> {
                    File directory = new File(STUDENTS_PATH);
                    File[] studentDirectories = directory.listFiles();
                    if (studentDirectories == null) {
                        tracker.finish();
                        return;
                    }
                    tracker.setMaximum(studentDirectories.length);
                    for (File studentDirectory : studentDirectories) {
                        try {
                            students.add(new Student(studentDirectory.getName()));
                        } catch (InvalidFormatException ignored) {
                        } finally {
                            tracker.increment();
                        }
                    }
                    tracker.finish();
                }
        );
    }

    public void submitTask(ObserveOf startObserver, ObserveOf endObserver, String message,
                           Consumer<ProgressTracker> consumer) {
        ProgressTracker tracker = getTracker(message);
        notify(ObserveOf.ON_TASK_START, tracker);
        notify(startObserver);
        executorService.submit(() -> {
            consumer.accept(tracker);
            notify(endObserver);
            notify(ObserveOf.ON_TASK_END, tracker);
        });
    }

    public Verifier getVerifier() {
        return verifier;
    }

    public List<ProgressTracker> getTrackers() {
        return trackers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public enum ObserveOf {
        ON_TASK_START,
        ON_TASK_END,

        ON_START_VERIFIER_LOAD,
        ON_VERIFIER_LOADED,

        ON_START_VERIFY,
        ON_VERIFIED,

        ON_START_STUDENT_LOAD,
        ON_STUDENT_LOADED;

        private final ObserveOf parent;

        ObserveOf() {
            this.parent = null;
        }

        ObserveOf(ObserveOf parent) {
            this.parent = parent;
        }

        public boolean isIn(ObserveOf other) {
            if (this == other) {
                return true;
            }
            if (parent != null) {
                return parent.isIn(other);
            }
            return false;
        }
    }
}
