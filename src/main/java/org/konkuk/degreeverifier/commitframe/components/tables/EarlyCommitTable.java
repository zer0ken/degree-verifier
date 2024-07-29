package org.konkuk.degreeverifier.commitframe.components.tables;

import org.konkuk.degreeverifier.commitframe.logic.tables.EarlyCommitTableModel;
import org.konkuk.degreeverifier.common.logic.TableResizer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

public class EarlyCommitTable extends JTable {
    public EarlyCommitTable() {
        setModel(new EarlyCommitTableModel());
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoCreateRowSorter(true);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        TableResizer.fitToContentWidth(this);
    }
}
