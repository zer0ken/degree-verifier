package org.konkuk.degreeverifier.logic.informationtree;

import org.konkuk.degreeverifier.business.InformationModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class InformationTargetListSelectionListener implements ListSelectionListener {
    private final InformationModel informationModel = InformationModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        List<Object> selected = ((JList) e.getSource()).getSelectedValuesList();
        if (selected.isEmpty()) {
            return;
        }
        informationModel.updateInformationTarget(selected);
    }
}
