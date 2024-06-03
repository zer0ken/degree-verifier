package org.konkuk.degreeverifier.logic.informationtree;

import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InformationTargetListSelectionListener implements ListSelectionListener {
    private final AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        appModel.setInformationTargets(((JList) e.getSource()).getSelectedValuesList());
    }
}
