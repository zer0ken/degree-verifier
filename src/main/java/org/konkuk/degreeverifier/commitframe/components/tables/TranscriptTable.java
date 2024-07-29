package org.konkuk.degreeverifier.commitframe.components.tables;

import org.konkuk.degreeverifier.commitframe.logic.tables.TranscriptTableModel;
import org.konkuk.degreeverifier.common.logic.TableResizer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

public class TranscriptTable extends JTable {
    public TranscriptTable() {
        setModel(new TranscriptTableModel());
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoCreateRowSorter(true);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        TableResizer.fitToContentWidth(this);
    }
}
