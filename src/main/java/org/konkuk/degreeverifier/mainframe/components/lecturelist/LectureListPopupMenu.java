package org.konkuk.degreeverifier.mainframe.components.lecturelist;

import org.konkuk.degreeverifier.mainframe.actions.OpenLectureDirectoryAction;

import javax.swing.*;

public class LectureListPopupMenu extends JPopupMenu {
    public LectureListPopupMenu() {
        add(new OpenLectureDirectoryAction());
    }
}
