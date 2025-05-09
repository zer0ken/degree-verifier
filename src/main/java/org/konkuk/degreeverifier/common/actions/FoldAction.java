package org.konkuk.degreeverifier.common.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.common.components.FoldableTree;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.degreeverifier.ui.Strings.FOLD_INFORMATION_TREE;

public class FoldAction extends AbstractAction {
    private final FoldableTree tree;

    public FoldAction(FoldableTree tree) {
        this.tree = tree;
        putValue(NAME, FOLD_INFORMATION_TREE);
        putValue(SHORT_DESCRIPTION, FOLD_INFORMATION_TREE);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/fold_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tree.fold();
    }
}
