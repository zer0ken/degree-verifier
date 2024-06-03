package org.konkuk.degreeverifier.components.lecturelist;

import org.konkuk.degreeverifier.actions.OpenLectureDirectoryAction;

import javax.swing.*;

public class LectureListPopupMenu extends JPopupMenu {
    public LectureListPopupMenu() {
        add(new OpenLectureDirectoryAction());
    }
}
