package org.konkuk.degreeverifier.editorframe.components.verifierlist;

import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.logic.verifierlist.VerifierListModel;
import org.konkuk.degreeverifier.editorframe.logic.verifierlist.VerifierListSelectionListener;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_VERIFIER_LIST_SIZE;

public class VerifierListPanel extends JPanel {
    public VerifierListPanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_VERIFIER_LIST_SIZE);

        JList<EditableDegreeCriteria> list = new JList<>();
        list.setBackground(UIManager.getColor("RootPane.background"));
        list.setModel(new VerifierListModel());
        list.addListSelectionListener(new VerifierListSelectionListener());

        JScrollPane scrollPane = new JScrollPane(
                list,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new VerifierListToolbar(), BorderLayout.NORTH);
        add(scrollPane);
    }
}
