package org.konkuk.degreeverifier.editorframe.components.editpanel;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;

public class EditPanel extends JPanel {
    public EditPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_EDIT_PANEL_SIZE);

        JPanel editInner = new JPanel(new BorderLayout());
        editInner.add(new EditInnerPannel(), BorderLayout.NORTH);

        JScrollPane editScrollPane = new JScrollPane(
                editInner,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(editScrollPane);
        add(new EditToolbar(), BorderLayout.NORTH);
    }
}
