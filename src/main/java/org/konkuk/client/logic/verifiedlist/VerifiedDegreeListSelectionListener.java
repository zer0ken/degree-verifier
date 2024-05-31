package org.konkuk.client.logic.verifiedlist;

import org.konkuk.client.AppModel;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifiedDegreeListSelectionListener implements ListSelectionListener {
    final private AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()){
            return;
        }
        JList<DegreeSnapshot> list = (JList<DegreeSnapshot>) e.getSource();
        appModel.setSelectedVerifiedDegree(list.getSelectedValuesList());
    }
}
