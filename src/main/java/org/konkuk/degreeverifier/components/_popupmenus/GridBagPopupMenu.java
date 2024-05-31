package org.konkuk.degreeverifier.components._popupmenus;

import javax.swing.*;
import java.awt.*;

public class GridBagPopupMenu extends JPopupMenu {
    protected final GridBagConstraints constraints;

    public GridBagPopupMenu() {
        setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
    }
}
