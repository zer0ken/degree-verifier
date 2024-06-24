package org.konkuk.degreeverifier.common.logic;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class SimplifiedDocumentListener implements DocumentListener {
    private final Consumer<DocumentEvent> eventHandler;

    public SimplifiedDocumentListener(Consumer<DocumentEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        eventHandler.accept(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        eventHandler.accept(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        eventHandler.accept(e);

    }
}
