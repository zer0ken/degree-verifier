package org.konkuk.degreeverifier.commitframe.components.committedlist;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListMode;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListMouseAdapter;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListSelectionListener;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTargetFocusListener;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTargetListSelectionListener;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Borders.TOP_MARGIN;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_COMMITTED_DEGREE_SIZE;

public class CommittedDegreePanel extends JPanel {
    public CommittedDegreePanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_COMMITTED_DEGREE_SIZE);

        JList<DegreeSnapshot> list = new JList<>();
        list.setBorder(new CompoundBorder(TOP_MARGIN, list.getBorder()));
        list.setModel(new CommittedDegreeListMode());
        list.addListSelectionListener(new CommittedDegreeListSelectionListener());
        list.setComponentPopupMenu(new CommittedDegreePopupMenu());
        list.addMouseListener(new CommittedDegreeListMouseAdapter());

        list.addListSelectionListener(new InformationTargetListSelectionListener());
        list.addFocusListener(new InformationTargetFocusListener());

        JScrollPane scrollPane = new JScrollPane(
                list,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new CommittedDegreeToolbar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
