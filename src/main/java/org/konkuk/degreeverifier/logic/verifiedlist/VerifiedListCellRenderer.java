package org.konkuk.degreeverifier.logic.verifiedlist;

import org.konkuk.degreeverifier.components.verifiedlist.VerifiedDegreeListNullCell;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_DEGREE_FOREGROUND;

public class VerifiedListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value == null) {
            return new VerifiedDegreeListNullCell();
        }
        setHorizontalAlignment(LEFT);
        VerifiedDegreeListModel model = (VerifiedDegreeListModel) list.getModel();
        int nullIndex = model.indexOf(null);
        if (nullIndex != -1 && index > nullIndex) {
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionInactiveBackground"));
            }
            setForeground(INSUFFICIENT_DEGREE_FOREGROUND);
        }
        return this;
    }
}
