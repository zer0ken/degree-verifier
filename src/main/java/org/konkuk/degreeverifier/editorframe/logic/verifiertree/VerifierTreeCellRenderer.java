package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Colors.DEFAULT_SVG_COLOR;
import static org.konkuk.degreeverifier.ui.Dimensions.ITEM_ICON_SIZE;

public class VerifierTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JComponent component = (JComponent) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object data = node.getUserObject();
        if (data instanceof DegreeCriteria) {
            setText("<html><b>" + getText() + "</b></html>");
            setIcon("icons/verified_icon.svg");
        } else if (data instanceof RecursiveCriteria) {
            RecursiveCriteria recursive = (RecursiveCriteria) data;
            if (recursive instanceof EditableRecursiveCriteria && ((EditableRecursiveCriteria) recursive).removed) {
                setText("<html><strike>" + getText() + "</strike></html>");
            }
            if (recursive.lectureCriteria != null) {
                setIcon("icons/book_icon.svg", component.getForeground());
            } else {
                setIcon("icons/rule_icon.svg", component.getForeground());
            }
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
