package org.konkuk.degreeverifier.logic.informationtree;

import org.konkuk.degreeverifier.business.InformationModel;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class InformationTargetFocusListener implements FocusListener {
    private final InformationModel informationModel = InformationModel.getInstance();

    @Override
    public void focusGained(FocusEvent e) {
        JList list = (JList) e.getSource();
        List<Object> selected = (List<Object>) list.getSelectedValuesList();
        if (selected.isEmpty()) {
            return;
        }
        informationModel.updateInformationTarget(list.getSelectedValuesList());
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
