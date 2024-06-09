package org.konkuk.degreeverifier.common.components;

import static org.konkuk.degreeverifier.ui.Borders.TITLED_TOOLBAR_BORDER2;
import static org.konkuk.degreeverifier.ui.Colors.COMMON_WHITE;

public class InnerTitledToolbar extends TitledToolbar {
    public InnerTitledToolbar(String title) {
        super(title);
        setBackground(COMMON_WHITE);
        setBorder(TITLED_TOOLBAR_BORDER2);
    }
}
