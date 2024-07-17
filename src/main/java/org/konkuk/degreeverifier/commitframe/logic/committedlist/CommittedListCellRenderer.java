package org.konkuk.degreeverifier.commitframe.logic.committedlist;

import org.konkuk.degreeverifier.commitframe.components.committedlist.separators.EarlyCommittedSeparatorCell;
import org.konkuk.degreeverifier.commitframe.components.committedlist.separators.NewlyCommittedSeparatorCell;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.EarlyCommittedSeparatorItem;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.items.NewlyCommittedSeparatorItem;

import javax.swing.*;
import java.awt.*;

public class CommittedListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof EarlyCommittedSeparatorItem) {
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionInactiveBackground"));
            }
            return new EarlyCommittedSeparatorCell();
        }
        if (value instanceof NewlyCommittedSeparatorItem) {
            return new NewlyCommittedSeparatorCell();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
