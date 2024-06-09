package org.konkuk.degreeverifier.mainframe.logic.verifiedlist;

import org.konkuk.degreeverifier.mainframe.components.verifiedlist.items.VerifiedDegreeListInsufficientSeparatorCell;
import org.konkuk.degreeverifier.mainframe.components.verifiedlist.items.VerifiedDegreeListSufficientSeparatorCell;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.VerifiedDegreeListInsufficientItem;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.VerifiedDegreeListInsufficientSeparatorItem;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.VerifiedDegreeListSufficientSeparatorItem;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_DEGREE_FOREGROUND;

public class VerifiedListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof VerifiedDegreeListInsufficientSeparatorItem) {
            return new VerifiedDegreeListInsufficientSeparatorCell();
        }
        if (value instanceof VerifiedDegreeListSufficientSeparatorItem) {
            return new VerifiedDegreeListSufficientSeparatorCell();
        }
        if (value instanceof VerifiedDegreeListInsufficientItem){
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionInactiveBackground"));
            }
            setForeground(INSUFFICIENT_DEGREE_FOREGROUND);
            return this;
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
