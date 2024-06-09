package org.konkuk.degreeverifier.mainframe.components.lecturelist;

import org.konkuk.degreeverifier.common.components.InnerTitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.OpenLectureDirectoryAction;

import static org.konkuk.degreeverifier.ui.Strings.LECTURE_LIST;

public class LectureListToolbar extends InnerTitledToolbar {
    public LectureListToolbar() {
        super(LECTURE_LIST);
        add(new OpenLectureDirectoryAction());
    }
}
