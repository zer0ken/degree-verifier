package org.konkuk.degreeverifier.logic.verifiedlist;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Arrays;

public class VerifiedDegreeListSelectionListener implements ListSelectionListener {
    final private AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList<DegreeSnapshot> list = (JList<DegreeSnapshot>) e.getSource();

        if (list.getSelectedValuesList().contains(null)) {
            list.setSelectedIndices(
                    Arrays.stream(list.getSelectedIndices()).filter(i -> list.getModel().getElementAt(i) != null).toArray()
            );
            return;
        }

        appModel.setSelectedVerifiedDegree(list.getSelectedValuesList());
    }
}
