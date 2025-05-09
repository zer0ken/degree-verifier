package org.konkuk.degreeverifier.ui;

import java.awt.*;

public class Colors {
    public static final Color DEFAULT_SVG_COLOR = new Color(0x5f6368);

    public static final Color DEFAULT_BACKGROUND = new Color(0xf7f8fa);

    public static final Color COMMON_BLACK = new Color(0x000000);
    public static final Color COMMON_GRAY = new Color(0x857f7f);
    public static final Color COMMON_WHITE = new Color(0xFFFFFF);
    public static final Color COMMON_RED = new Color(0xFF6262);
    public static final Color COMMON_ORANGE = new Color(0xeb8c25);
    public static final Color COMMON_GREEN = new Color(0x088147);

    public static final Color DESCRIPTION = new Color(0x5B5B5B);

    public static final Color SUFFICIENT_SEPARATOR_FOREGROUND = COMMON_GREEN;
    public static final Color INSUFFICIENT_SEPARATOR_FOREGROUND = COMMON_RED;
    public static final Color NOT_VERIFIED_SEPARATOR_FOREGROUND = COMMON_GRAY;

    public static final Color INSUFFICIENT_DEGREE_FOREGROUND = COMMON_RED;
    public static final Color NOT_VERIFIED_DEGREE_FOREGROUND = COMMON_GRAY;

    public static final Color INFORMATION_INSUFFICIENT_ROOT_NODE_FOREGROUND = COMMON_RED;

    public static final Color NOT_PASSED_NODE_FOREGROUND = COMMON_GRAY;
    public static final Color DUPLICATED_NODE_FOREGROUND = COMMON_ORANGE;
    public static final Color PASSED_NODE_FOREGROUND = COMMON_GREEN;

    public static final Color EARLY_COMMITTED_SEPARATOR_FOREGROUND = COMMON_GRAY;
    public static final Color NEWLY_COMMITTED_SEPARATOR_FOREGROUND = COMMON_GREEN;
}
