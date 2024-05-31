package org.konkuk.client.components.verifiedlist;

import org.konkuk.client.logic.verifiedlist.VerifiedDegreeListModel;
import org.konkuk.client.logic.verifiedlist.VerifiedDegreeListSelectionListener;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import javax.swing.*;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.client.ui.Dimensions.MINIMUM_VERIFIED_DEGREE_SIZE;

public class VerifiedDegreePanel extends JPanel {
    public VerifiedDegreePanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_VERIFIED_DEGREE_SIZE);

        JList<DegreeSnapshot> list = new JList<>();
        list.setModel(new VerifiedDegreeListModel());
        list.addListSelectionListener(new VerifiedDegreeListSelectionListener());
        list.setComponentPopupMenu(new VerifiedDegreePopupMenu());

        JScrollPane scrollPane = new JScrollPane(
                list,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new VerifiedDegreeToolbar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
