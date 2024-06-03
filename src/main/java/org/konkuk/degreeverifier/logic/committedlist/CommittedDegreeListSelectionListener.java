package org.konkuk.degreeverifier.logic.committedlist;


import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CommittedDegreeListSelectionListener implements ListSelectionListener {
    final private AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()){
            return;
        }
        JList<DegreeSnapshot> list = (JList<DegreeSnapshot>) e.getSource();
        appModel.setSelectedCommittedDegree(list.getSelectedValuesList());
    }
}
