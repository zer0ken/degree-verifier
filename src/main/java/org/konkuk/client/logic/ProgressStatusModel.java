package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.client.components.statusbar.progress.ProgressStatusPanel;

import javax.swing.*;

import static org.konkuk.client.ui.Strings.NO_TASK;
import static org.konkuk.client.ui.Strings.TASKS_IN_PROGRESS;

public class ProgressStatusModel {
    private final AppModel appModel = AppModel.getInstance();

    private final ProgressStatusPanel panel;
    private final BoundedRangeModel trackAll;

    public ProgressStatusModel(ProgressStatusPanel panel) {
        this.panel = panel;
        trackAll = new DefaultBoundedRangeModel();
        trackAll.setMaximum(1);

        panel.getProgressBar().setModel(trackAll);

        appModel.observe(AppModel.ObserveOf.ON_TASK_STARTED, this::taskStarted);
        appModel.observe(AppModel.ObserveOf.ON_TASK_FINISHED, this::taskFinished);
    }

    public void taskStarted(Object _tracker) {
        ProgressTracker tracker = (ProgressTracker) _tracker;

        panel.getProgressBar().setVisible(true);

        if (appModel.getTrackers().size() == 1) {
            panel.getProgressBar().setModel(tracker);
            updateLabel(tracker.message);
        } else {
            panel.getProgressBar().setModel(trackAll);
            trackAll.setMaximum(trackAll.getMaximum() + 1);
            updateLabel(String.format(TASKS_IN_PROGRESS, trackAll.getValue(), trackAll.getMaximum()));
        }

        Runnable onFinish = panel.getPopup().add(tracker);

        tracker.addOnFinishedListener(() ->
                SwingUtilities.invokeLater(onFinish)
        );
    }

    public void taskFinished(Object unused) {
        if (appModel.getTrackers().size() == 1) {
            ProgressTracker tracker = appModel.getTrackers().get(0);
            panel.getProgressBar().setModel(tracker);
            updateLabel(tracker.message);
        } else if (appModel.getTrackers().isEmpty()) {
            updateLabel(NO_TASK);
            panel.getProgressBar().setModel(null);
            panel.getProgressBar().setVisible(false);
            trackAll.setMaximum(1);
            panel.getPopup().setVisible(false);
        } else {
            trackAll.setValue(trackAll.getValue() + 1);
            updateLabel(String.format(TASKS_IN_PROGRESS, trackAll.getValue(), trackAll.getMaximum()));
        }
    }

    public void updateLabel(String label) {
        panel.getLabel().setText(label);
        panel.getPopup().setTitle(label);
    }
}
