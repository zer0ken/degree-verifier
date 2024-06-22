package org.konkuk.degreeverifier.editorframe.components;

import org.konkuk.degreeverifier.editorframe.components.editpanel.EditPanel;
import org.konkuk.degreeverifier.editorframe.components.verifierlist.VerifierListPanel;
import org.konkuk.degreeverifier.editorframe.components.verifiertree.VerifierTreePanel;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;

public class SplitPanel extends JPanel {
    public SplitPanel() {
        setLayout(new BorderLayout());

        JPanel inner = new JPanel(new BorderLayout());
        inner.add(new EditPanel(), BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(
                inner,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JSplitPane editPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new VerifierTreePanel(),
                scrollPane
        );
        editPane.setResizeWeight(0.7);

        JSplitPane withVerifierList = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new VerifierListPanel(),
                editPane
        );

        add(withVerifierList);
    }
}
