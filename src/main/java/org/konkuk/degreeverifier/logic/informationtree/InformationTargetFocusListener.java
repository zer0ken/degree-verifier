package org.konkuk.degreeverifier.logic.informationtree;

import org.konkuk.degreeverifier.business.AppModel;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InformationTargetFocusListener implements FocusListener {
    private final AppModel appModel = AppModel.getInstance();

    @Override
    public void focusGained(FocusEvent e) {
        appModel.setInformationTargets(((JList) e.getSource()).getSelectedValuesList());
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
