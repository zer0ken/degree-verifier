package org.konkuk.degreeverifier.business;

import javax.swing.*;
import java.util.*;
import java.util.function.Consumer;

public abstract class Observable {
    protected final Map<Event, List<Consumer<Object>>> observerMap = Collections.synchronizedMap(new HashMap<>());

    public void observe(Event on, Consumer<Object> callback) {
        if (on == null) {
            return;
        }
        if (!observerMap.containsKey(on)) {
            observerMap.put(on, Collections.synchronizedList(new LinkedList<>()));
        }
        observerMap.get(on).add(callback);
    }

    protected void notify(Event on, Object o) {
        if (on == null) {
            return;
        }
        List<Consumer<Object>> observers = observerMap.get(on);
        if (observers != null) {
            observers.forEach(observer -> SwingUtilities.invokeLater(() -> observer.accept(o)));
        }
    }

    public interface Event {
    }
}
