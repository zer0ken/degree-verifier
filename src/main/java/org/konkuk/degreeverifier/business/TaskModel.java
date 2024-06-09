package org.konkuk.degreeverifier.business;

import org.konkuk.degreeverifier.common.logic.statusbar.ProgressTracker;

import javax.swing.*;
import java.util.*;
import java.util.function.Consumer;

public class TaskModel {
    protected static final TaskModel instance = new TaskModel();

    protected TaskModel() {
    }

    public static TaskModel getInstance() {
        return instance;
    }

    private final List<ProgressTracker> trackers = Collections.synchronizedList(new LinkedList<>());
    private final Map<ObserveOn, List<Consumer<ProgressTracker>>> observers = Collections.synchronizedMap(new LinkedHashMap<>());

    public void observe(ObserveOn observeOn, Consumer<ProgressTracker> callback) {
        if (!observers.containsKey(observeOn)) {
            observers.put(observeOn, new LinkedList<>());
        }
        observers.get(observeOn).add(callback);
    }

    public void notify(ObserveOn observeOn, ProgressTracker tracker) {
        List<Consumer<ProgressTracker>> observers = this.observers.get(observeOn);
        if (observers != null) {
            SwingUtilities.invokeLater(()-> observers.forEach(observer -> observer.accept(tracker)));
        }
    }

    public void register(ProgressTracker tracker) {
        trackers.add(tracker);
        tracker.addFinishListener(() -> discard(tracker));
        notify(ObserveOn.ON_PROGRESS_REGISTERED, tracker);
    }

    public void discard(ProgressTracker tracker) {
        trackers.remove(tracker);
        notify(ObserveOn.ON_PROGRESS_DISCARDED, tracker);
    }

    public List<ProgressTracker> getTrackers() {
        return trackers;
    }

    public enum ObserveOn {
        ON_PROGRESS_REGISTERED,
        ON_PROGRESS_DISCARDED
    }
}
