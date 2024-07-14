package org.konkuk.degreeverifier.commitframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items.ListItem;
import org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items.SeparatorItem;

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
        JList<ListItem> list = (JList<ListItem>) e.getSource();

        if (list.getSelectedValuesList().stream().anyMatch(item -> item instanceof SeparatorItem)) {
            list.setSelectedIndices(Arrays.stream(list.getSelectedIndices())
                    .filter(i -> !(list.getModel().getElementAt(i) instanceof SeparatorItem))
                    .toArray()
            );
            return;
        }

        appModel.setSelectedVerifiedDegree(list.getSelectedValuesList().stream()
                .map(ListItem::getDegreeSnapshot)
                .collect(Collectors.toList())
        );
    }
}
