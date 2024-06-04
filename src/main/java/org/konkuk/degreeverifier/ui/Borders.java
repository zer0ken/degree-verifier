package org.konkuk.degreeverifier.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class Borders {
    public static final Border SEPARATOR_EVERY_SIDE = new MatteBorder(1, 1, 1, 1, UIManager.getColor("Separator.foreground"));
    public static final Border SEPARATOR_WITHOUT_BOTTOM = new MatteBorder(1, 1, 0, 1, UIManager.getColor("Separator.foreground"));

    public static final Border TOP_SEPARATOR = new MatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground"));
    public static final Border LEFT_SEPARATOR = new MatteBorder(0, 1, 0, 0, UIManager.getColor("Separator.foreground"));
    public static final Border BOTTOM_SEPARATOR = new MatteBorder(0, 0, 1, 0, UIManager.getColor("Separator.foreground"));
    public static final Border RIGHT_SEPARATOR = new MatteBorder(0, 0, 0, 1, UIManager.getColor("Separator.foreground"));

    public static final Border TOP_MARGIN = new EmptyBorder(4, 0, 0, 1);

    public static final Border TITLED_TOOLBAR_BORDER = new EmptyBorder(2, 12, 2, 3);
    public static final Border TITLED_TOOLBAR_BORDER2 = new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, UIManager.getColor("Separator.foreground")),
            TITLED_TOOLBAR_BORDER
    );
}
