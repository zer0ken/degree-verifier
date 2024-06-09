package org.konkuk.degreeverifier.mainframe.logic.verifiedlist;

import org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators.InsufficientSeparatorCell;
import org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators.NotVerifiedSeparatorCell;
import org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators.SufficientSeparatorCell;
import org.konkuk.degreeverifier.mainframe.logic.verifiedlist.items.*;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_DEGREE_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Colors.NOT_VERIFIED_DEGREE_FOREGROUND;

public class VerifiedListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof InsufficientSeparatorItem) {
            return new InsufficientSeparatorCell();
        }
        if (value instanceof SufficientSeparatorItem) {
            return new SufficientSeparatorCell();
        }
        if (value instanceof NotVerifiedSeparatorItem) {
            return new NotVerifiedSeparatorCell();
        }
        if (value instanceof InsufficientItem){
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionInactiveBackground"));
            }
            setForeground(INSUFFICIENT_DEGREE_FOREGROUND);
            return this;
        }
        if (value instanceof NotVerifiedItem) {
            if (isSelected) {
                setBackground(UIManager.getColor("List.selectionInactiveBackground"));
            }
            setForeground(NOT_VERIFIED_DEGREE_FOREGROUND);
            return this;
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
