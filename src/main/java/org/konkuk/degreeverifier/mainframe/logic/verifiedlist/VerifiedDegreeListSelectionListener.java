package org.konkuk.degreeverifier.mainframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.VerifiedDegreeListItem;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.VerifiedDegreeListSeparatorItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VerifiedDegreeListSelectionListener implements ListSelectionListener {
    final private AppModel appModel = AppModel.getInstance();

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        JList<VerifiedDegreeListItem> list = (JList<VerifiedDegreeListItem>) e.getSource();

        if (list.getSelectedValuesList().stream().anyMatch(item -> item instanceof VerifiedDegreeListSeparatorItem)) {
            list.setSelectedIndices(Arrays.stream(list.getSelectedIndices())
                    .filter(i -> !(list.getModel().getElementAt(i) instanceof VerifiedDegreeListSeparatorItem))
                    .toArray()
            );
            return;
        }

        appModel.setSelectedVerifiedDegree(list.getSelectedValuesList().stream()
                .map(VerifiedDegreeListItem::getDegreeSnapshot)
                .collect(Collectors.toList())
        );
    }
}
