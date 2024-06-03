package org.konkuk.degreeverifier.logic.statusbar;

import org.konkuk.degreeverifier.business.TaskModel;
import org.konkuk.degreeverifier.components.statusbar.ProgressStatusPopupMenu;

import static org.konkuk.degreeverifier.ui.Strings.TASKS_IN_PROGRESS_DETAILED;

public class ProgressStatusPopupMenuModel {
    private final TaskModel taskModel = TaskModel.getInstance();

    private ProgressStatusPopupMenu popupMenu;

    public ProgressStatusPopupMenuModel(ProgressStatusPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
        taskModel.observe(TaskModel.ObserveOn.ON_PROGRESS_REGISTERED, this::trackerRegistered);
        taskModel.observe(TaskModel.ObserveOn.ON_PROGRESS_DISCARDED, this::trackerDiscarded);
    }

    private void trackerRegistered(ProgressTracker tracker) {
        popupMenu.setText(String.format(TASKS_IN_PROGRESS_DETAILED, taskModel.getTrackers().size()));
        popupMenu.add(tracker);
    }

    private void trackerDiscarded(ProgressTracker tracker) {
        popupMenu.setText(String.format(TASKS_IN_PROGRESS_DETAILED, taskModel.getTrackers().size()));
        if (taskModel.getTrackers().isEmpty()) {
            popupMenu.setVisible(false);
        }
    }
}
