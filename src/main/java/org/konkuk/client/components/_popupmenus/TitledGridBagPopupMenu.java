package org.konkuk.client.components._popupmenus;

import javax.swing.*;

public class TitledGridBagPopupMenu extends GridBagPopupMenu {
    protected final JLabel titleLabel;

    public TitledGridBagPopupMenu() {
        titleLabel = new JLabel();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
