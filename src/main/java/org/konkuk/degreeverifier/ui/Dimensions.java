package org.konkuk.degreeverifier.ui;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dimensions {
    public static final Dimension MINIMUM_APP_SIZE = new Dimension(740, 500);
    public static final Dimension PREFERRED_APP_SIZE = new Dimension(1240, 760);

    public static final Dimension MINIMUM_STUDENT_LIST_SIZE = new Dimension(180, 180);
    public static final Dimension MINIMUM_COMMITTED_DEGREE_SIZE = new Dimension(220, 180);
    public static final Dimension MINIMUM_VERIFIED_DEGREE_SIZE = new Dimension(220, 180);

    public static final Dimension MINIMUM_LECTURE_LIST_SIZE = new Dimension(220, 180);
    public static final Dimension PREFERRED_LECTURE_LIST_SIZE = new Dimension(220, 280);

    public static final Dimension MINIMUM_INFORMATION_PANEL_SIZE = new Dimension(180, 180);
    public static final Dimension PREFERRED_INFORMATION_PANEL_SIZE = new Dimension(300, 0);

    public static final Dimension STATUS_BAR_SIZE = new Dimension(0, 24);
    public static final EmptyBorder STATUS_BAR_PADDING = new EmptyBorder(2, 14, 2, 14);

    public static final Dimension TITLED_TOOLBAR_SIZE = new Dimension(0, 38);
    public static final Dimension TITLED_TOOLBAR_BUTTON_SIZE = new Dimension(34, 34);
    public static final float TITLED_TOOLBAR_ICON_SCALE = 0.8f;
    public static final int TITLED_TOOLBAR_LEFT_INSET = 12;
    public static final int TITLED_TOOLBAR_RIGHT_GAP = 26;
    public static final int TITLED_TOOLBAR_NARROW_LIMIT = 300;

    public static final Insets PROGRESS_GRID_INSETS = new Insets(4, 8, 4, 8);
    public static final EmptyBorder PROGRESS_PANEL_BORDER = new EmptyBorder(0, 8, 0, 8);
    public static final int PROGRESS_BAR_LEFT_GAP = 8;

    public static final int ITEM_ICON_SIZE = 16;
}
