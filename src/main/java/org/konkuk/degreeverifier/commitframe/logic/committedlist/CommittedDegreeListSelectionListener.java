package org.konkuk.degreeverifier.commitframe.logic.committedlist;


import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items.SeparatorItem;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommittedDegreeListSelectionListener implements ListSelectionListener {
    final private AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList<VerifierListItem> list = (JList<VerifierListItem>) e.getSource();

        if (list.getSelectedValuesList().stream().anyMatch(item -> item instanceof SeparatorItem)) {
            list.setSelectedIndices(Arrays.stream(list.getSelectedIndices())
                    .filter(i -> !(list.getModel().getElementAt(i) instanceof SeparatorItem))
                    .toArray()
            );
            return;
        }

        appModel.setSelectedCommittedDegree(list.getSelectedValuesList().stream()
                .map(VerifierListItem::getDegreeVerifier)
                .collect(Collectors.toList())
        );
    }
}
