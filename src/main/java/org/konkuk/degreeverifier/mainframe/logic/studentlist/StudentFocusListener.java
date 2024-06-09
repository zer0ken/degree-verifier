package org.konkuk.degreeverifier.mainframe.logic.studentlist;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class StudentFocusListener implements FocusListener {
    private final StudentListSelectionModel selectionModel = StudentListSelectionModel.getInstance();

    @Override
    public void focusGained(FocusEvent e) {
        selectionModel.resetSelection();
    }

    @Override
    public void focusLost(FocusEvent e) {
        selectionModel.resetSelection();
    }
}
