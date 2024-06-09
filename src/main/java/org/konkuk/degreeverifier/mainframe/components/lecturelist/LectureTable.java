package org.konkuk.degreeverifier.mainframe.components.lecturelist;

import org.konkuk.degreeverifier.mainframe.logic.lecturelist.LectureTableModel;

import javax.swing.*;

public class LectureTable extends JTable {
    public LectureTable() {
        super();
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setModel(new LectureTableModel());
        setComponentPopupMenu(new LectureListPopupMenu());
        setEnabled(false);
    }
}
