package org.konkuk.degreeverifier.common.logic;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TableResizer {
    public static void fitToContentWidth(final JTable table) {
        if (table.getRowCount() < 0) {
            return;
        }
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int width = tableColumn.getPreferredWidth();
            for (int row = 0; row < Math.min(table.getRowCount(), 5); ++row) {
                final Object cellValue = table.getValueAt(row, column);
                final TableCellRenderer renderer = table.getCellRenderer(row, column);
                final Component comp = renderer.getTableCellRendererComponent(table, cellValue, false, false, row, column);
                width = Math.max(width,
                        comp.getPreferredSize().width
                );
            }
            final TableColumn tc = table.getColumn(table.getColumnName(column));
            width += table.getIntercellSpacing().width * 2;
            tc.setPreferredWidth(width);
            tc.setMinWidth(width);
        }
    }
}
