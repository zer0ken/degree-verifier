package org.konkuk.degreeverifier.mainframe.logic.informationtree;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.mainframe.logic.informationtree.nodes.InsufficientRootNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.*;
import static org.konkuk.degreeverifier.ui.Dimensions.ITEM_ICON_SIZE;

public class InformationTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JComponent component = (JComponent) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object data = node.getUserObject();
        if (data instanceof DegreeSnapshot) {
            setText("<html><b>" + getText() + "</b></html>");
            setIcon("icons/verified_icon.svg");
        } else if (data instanceof RecursiveSnapshot) {
            RecursiveSnapshot recursive = (RecursiveSnapshot) data;
            if (recursive.lectureSnapshot != null) {
                LectureSnapshot lecture = recursive.lectureSnapshot;
                if (lecture.matched == null) {
                    component.setForeground(NOT_PASSED_NODE_FOREGROUND);
                } else if (lecture.duplicatedDegrees.length > 0) {
                    component.setForeground(DUPLICATED_NODE_FOREGROUND);
                } else {
                    component.setForeground(PASSED_NODE_FOREGROUND);
                }
                setIcon("icons/book_icon.svg", component.getForeground());
            } else {
                if (!recursive.verified) {
                    component.setForeground(NOT_PASSED_NODE_FOREGROUND);
                } else {
                    component.setForeground(PASSED_NODE_FOREGROUND);
                }
                setIcon("icons/rule_icon.svg", component.getForeground());
            }
        } else if (data instanceof InsufficientRootNode) {
            component.setForeground(INFORMATION_INSUFFICIENT_ROOT_NODE_FOREGROUND);
            setIcon("icons/warning_icon.svg", component.getForeground());
        }
        return component;
    }

    public void setIcon(String name, Color color) {
        FlatSVGIcon icon = new FlatSVGIcon(name, ITEM_ICON_SIZE, ITEM_ICON_SIZE, getClass().getClassLoader());
        icon.setColorFilter(new FlatSVGIcon.ColorFilter());
        icon.getColorFilter().add(DEFAULT_SVG_COLOR, color, color);
        super.setIcon(icon);
    }

    public void setIcon(String name) {
        FlatSVGIcon icon = new FlatSVGIcon(name, ITEM_ICON_SIZE, ITEM_ICON_SIZE, getClass().getClassLoader());
        icon.setColorFilter(new FlatSVGIcon.ColorFilter());
        icon.getColorFilter().add(DEFAULT_SVG_COLOR,
                UIManager.getColor("Tree.textForeground"), UIManager.getColor("Tree.textForeground"));
        super.setIcon(icon);
    }
}
