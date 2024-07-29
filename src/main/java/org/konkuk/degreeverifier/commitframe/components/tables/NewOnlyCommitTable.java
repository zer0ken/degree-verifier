package org.konkuk.degreeverifier.commitframe.components.tables;

import org.konkuk.degreeverifier.commitframe.logic.tables.NewOnlyCommitTableModel;
import org.konkuk.degreeverifier.common.logic.TableResizer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

public class NewOnlyCommitTable extends JTable {
    public NewOnlyCommitTable() {
        setModel(new NewOnlyCommitTableModel());
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoCreateRowSorter(true);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        TableResizer.fitToContentWidth(this);
    }
}
