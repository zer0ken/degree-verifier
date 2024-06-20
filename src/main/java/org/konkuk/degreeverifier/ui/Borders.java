package org.konkuk.degreeverifier.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class Borders {
    public static final Border SEPARATOR_EVERY_SIDE = new MatteBorder(1, 1, 1, 1, UIManager.getColor("Separator.foreground"));

    public static final Border TOP_SEPARATOR = new MatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground"));
    public static final Border LEFT_SEPARATOR = new MatteBorder(0, 1, 0, 0, UIManager.getColor("Separator.foreground"));
    public static final Border BOTTOM_SEPARATOR = new MatteBorder(0, 0, 1, 0, UIManager.getColor("Separator.foreground"));
    public static final Border RIGHT_SEPARATOR = new MatteBorder(0, 0, 0, 1, UIManager.getColor("Separator.foreground"));

    public static final EmptyBorder PROGRESS_PANEL_BORDER = new EmptyBorder(0, 8, 0, 8);
    public static final EmptyBorder STATUS_BAR_PADDING = new EmptyBorder(2, 14, 2, 14);

    public static final Border TOP_MARGIN = new EmptyBorder(4, 0, 0, 1);

    public static final Border TITLED_TOOLBAR_BORDER = new EmptyBorder(2, 12, 2, 3);
    public static final Border TITLED_TOOLBAR_BORDER2 = new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, UIManager.getColor("Separator.foreground")),
            TITLED_TOOLBAR_BORDER
    );

    public static final Border LABELED_SEPARATOR_BORDER = new EmptyBorder(3, 16, 3, 16);
    public static final Border LABELED_SEPARATOR_BORDER2 = new EmptyBorder(6, 0, 6, 0);
    public static final Border EDIT_PANEL_BORDER = new EmptyBorder(3, 16, 3, 16);

    public static final Border APPLY_PANEL_BORDER = new EmptyBorder(12, 16, 12, 16);
}
