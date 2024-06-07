package org.konkuk.degreeverifier.components.verifiedlist;

import org.konkuk.degreeverifier.logic.informationtree.InformationTargetFocusListener;
import org.konkuk.degreeverifier.logic.informationtree.InformationTargetListSelectionListener;
import org.konkuk.degreeverifier.logic.verifiedlist.*;
import org.konkuk.degreeverifier.logic.verifiedlist.items.VerifiedDegreeListItem;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static org.konkuk.degreeverifier.ui.Borders.TOP_MARGIN;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_VERIFIED_DEGREE_SIZE;

public class VerifiedDegreePanel extends JPanel {
    public VerifiedDegreePanel() {
        setLayout(new BorderLayout());
        setMinimumSize(MINIMUM_VERIFIED_DEGREE_SIZE);

        JList<VerifiedDegreeListItem> list = new JList<>();
        list.setBorder(new CompoundBorder(TOP_MARGIN, list.getBorder()));
        list.setModel(new VerifiedDegreeListModel());
        list.addListSelectionListener(new VerifiedDegreeListSelectionListener());
        list.setComponentPopupMenu(new VerifiedDegreePopupMenu());
        list.setCellRenderer(new VerifiedListCellRenderer());
        list.addMouseListener(new VerifiedDegreeListMouseAdapter());
        list.addListSelectionListener(new InformationTargetListSelectionListener());
        list.addFocusListener(new InformationTargetFocusListener());
        new VerifiedDegreeListFocusRequester(list);

        JScrollPane scrollPane = new JScrollPane(
                list,
                VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        add(new VerifiedDegreeToolbar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
