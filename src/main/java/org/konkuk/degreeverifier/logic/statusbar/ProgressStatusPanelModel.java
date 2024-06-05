package org.konkuk.degreeverifier.logic.statusbar;

import org.konkuk.degreeverifier.business.TaskModel;
import org.konkuk.degreeverifier.components.statusbar.ProgressStatusPanel;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Strings.TASKS_IN_PROGRESS;

public class ProgressStatusPanelModel {
    private final TaskModel taskModel = TaskModel.getInstance();

    private final ProgressStatusPanel panel;

    private final DefaultBoundedRangeModel summaryModel = new DefaultBoundedRangeModel();

    public ProgressStatusPanelModel(ProgressStatusPanel panel) {
        this.panel = panel;
//        panel.setVisible(false);

        taskModel.observe(TaskModel.ObserveOn.ON_PROGRESS_REGISTERED, this::taskRegistered);
        taskModel.observe(TaskModel.ObserveOn.ON_PROGRESS_DISCARDED, this::taskDiscarded);
    }

    private void taskRegistered(ProgressTracker tracker) {
        panel.setVisible(true);
        summaryModel.setMaximum(summaryModel.getMaximum() + 1);

        if (taskModel.getTrackers().size() == 1) {
            panel.setModel(tracker);
            panel.setText(tracker.message);
        } else {
            panel.setModel(summaryModel);
            panel.setText(String.format(TASKS_IN_PROGRESS, summaryModel.getMaximum(), summaryModel.getValue()));
        }
    }

    private void taskDiscarded(ProgressTracker tracker) {
        switch (taskModel.getTrackers().size()) {
            case 0:
                panel.setVisible(false);
                summaryModel.setMaximum(0);
                summaryModel.setValue(0);
                break;
            case 1:
                ProgressTracker lastTracker = taskModel.getTrackers().get(0);
                panel.setText(lastTracker.message);
                panel.setModel(lastTracker);
                break;
            default:
                panel.setModel(summaryModel);
                panel.setText(String.format(TASKS_IN_PROGRESS, summaryModel.getMaximum(), summaryModel.getValue()));
                break;
        }
    }
}
