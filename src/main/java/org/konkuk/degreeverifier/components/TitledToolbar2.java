package org.konkuk.degreeverifier.components;

import static org.konkuk.degreeverifier.ui.Borders.TITLED_TOOLBAR_BORDER2;
import static org.konkuk.degreeverifier.ui.Colors.COMMON_WHITE;

public class TitledToolbar2 extends TitledToolbar {
    public TitledToolbar2(String title) {
        super(title);
        setBackground(COMMON_WHITE);
        setBorder(TITLED_TOOLBAR_BORDER2);
    }
}
