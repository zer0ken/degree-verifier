package org.konkuk.degreeverifier.editorframe.components;

import org.konkuk.degreeverifier.editorframe.components.editpanel.EditPanel;
import org.konkuk.degreeverifier.editorframe.components.verifierlist.VerifierListPanel;
import org.konkuk.degreeverifier.editorframe.components.verifiertree.VerifierTreePanel;

import javax.swing.*;
import java.awt.*;

public class SplitPanel extends JPanel {
    public SplitPanel() {
        setLayout(new BorderLayout());

        JSplitPane editPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new VerifierTreePanel(),
                new EditPanel()
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
