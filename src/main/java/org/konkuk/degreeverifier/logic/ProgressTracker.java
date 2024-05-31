package org.konkuk.degreeverifier.logic;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class ProgressTracker extends DefaultBoundedRangeModel {
    public final String message;

    private final List<Runnable> onFinishedListener;
    private boolean finished = false;

    public ProgressTracker(String message) {
        setValueIsAdjusting(true);

        this.message = message;
        onFinishedListener = new LinkedList<>();
    }

    public void increment() {
        setValue(getValue() + 1);
    }

    @Override
    public void setMaximum(int n) {
        super.setMaximum(n);
        setValueIsAdjusting(false);
    }

    public void addOnFinishedListener(Runnable onFinished) {
        if (finished) {
            onFinished.run();
        } else {
            onFinishedListener.add(onFinished);
        }
    }

    public void finish() {
        onFinishedListener.forEach(Runnable::run);
        finished = true;
    }
}
