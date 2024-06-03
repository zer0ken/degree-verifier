package org.konkuk.degreeverifier.logic.statusbar;

import org.konkuk.degreeverifier.business.TaskModel;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ProgressTracker extends DefaultBoundedRangeModel {
    private final TaskModel taskModel = TaskModel.getInstance();

    public final String message;

    private final List<Runnable> onFinishedListener = new LinkedList<>();
    private final List<Consumer<Integer>> onIncrementListener = new LinkedList<>();

    private boolean finished = false;

    public ProgressTracker(String message) {
        setValueIsAdjusting(true);
        taskModel.register(this);

        this.message = message;
    }

    @Override
    public void setMaximum(int n) {
        super.setMaximum(n);
        setValueIsAdjusting(false);
    }

    public void increment() {
        setValue(getValue() + 1);
        SwingUtilities.invokeLater(() -> onIncrementListener.forEach(onIncrement -> onIncrement.accept(getValue())));
    }

    public void finish() {
        finished = true;
        SwingUtilities.invokeLater(() -> onFinishedListener.forEach(Runnable::run));
    }

    public void addFinishListener(Runnable onFinished) {
        if (finished) {
            onFinished.run();
        } else {
            onFinishedListener.add(onFinished);
        }
    }

    public void addIncrementListener(Consumer<Integer> onIncrement) {
        onIncrementListener.add(onIncrement);
    }
}
