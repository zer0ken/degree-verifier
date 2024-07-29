package org.konkuk.degreeverifier.commitframe.components.lecturelist;

import org.konkuk.degreeverifier.commitframe.logic.lecturelist.LectureTableModel;
import org.konkuk.degreeverifier.common.logic.TableResizer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;

public class LectureTable extends JTable {
    public LectureTable() {
        super();
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoCreateRowSorter(true);
        setModel(new LectureTableModel());
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        TableResizer.fitToContentWidth(this);
    }
}
