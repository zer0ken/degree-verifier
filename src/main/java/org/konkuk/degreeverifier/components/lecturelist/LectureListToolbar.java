package org.konkuk.degreeverifier.components.lecturelist;

import org.konkuk.degreeverifier.actions.OpenLectureDirectoryAction;
import org.konkuk.degreeverifier.components.TitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.LECTURE_LIST;

public class LectureListToolbar extends TitledToolbar {
    public LectureListToolbar() {
        super(LECTURE_LIST);

        add(new OpenLectureDirectoryAction());
    }
}
