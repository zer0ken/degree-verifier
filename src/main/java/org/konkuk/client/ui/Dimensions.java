package org.konkuk.client.ui;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dimensions {
    public static final Dimension DEFAULT_APP_SIZE = new Dimension(1240, 720);

    public static final Dimension MINIMUM_APP_SIZE = new Dimension(800, 500);
    public static final Dimension MINIMUM_STUDENT_LIST_SIZE = new Dimension(160, 180);
    public static final Dimension MINIMUM_COMMITTED_DEGREE_SIZE = new Dimension(240, 180);
    public static final Dimension MINIMUM_VERIFIED_DEGREE_SIZE = new Dimension(240, 180);
    public static final Dimension MINIMUM_LECTURE_LIST_SIZE = new Dimension(240, 180);

    public static final Dimension MINIMUM_PROGRESS_LABEL_SIZE = new Dimension(120, 18);

    public static final Border TITLE_PANEL_PADDING = new EmptyBorder(4, 8, 4, 8);

    public static final Insets PROGRESS_GRID_INSETS = new Insets(4, 8, 4, 8);
}
