package org.konkuk.degreeverifier.commitframe.components.committedlist;

import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListModel;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListMouseAdapter;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedDegreeListSelectionListener;
import org.konkuk.degreeverifier.commitframe.logic.committedlist.CommittedListCellRenderer;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTargetFocusListener;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.InformationTargetListSelectionListener;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

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

        JList<VerifierListItem> list = new JList<>();
        list.setBorder(new CompoundBorder(TOP_MARGIN, list.getBorder()));
        list.setModel(new CommittedDegreeListModel());
        list.addListSelectionListener(new CommittedDegreeListSelectionListener());
        list.setComponentPopupMenu(new CommittedDegreePopupMenu());
        list.addMouseListener(new CommittedDegreeListMouseAdapter());
        list.setCellRenderer(new CommittedListCellRenderer());

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
