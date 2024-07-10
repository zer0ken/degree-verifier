package org.konkuk.degreeverifier.common.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.common.components.FoldableTree;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.UNFOLD_INFORMATION_TREE;

public class UnfoldAction extends AbstractAction {
    private final FoldableTree tree;

    public UnfoldAction(FoldableTree tree) {
        this.tree = tree;
        putValue(NAME, UNFOLD_INFORMATION_TREE);
        putValue(SHORT_DESCRIPTION, UNFOLD_INFORMATION_TREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/unfold_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tree.unfold();
    }
}
