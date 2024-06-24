package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.Editable;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class VerifierTreeSelectionListener implements TreeSelectionListener {
    private final EditorModel editorModel = EditorModel.getInstance();

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        for (TreePath path : e.getPaths()) {
            TreePath cur = path;
            Editable editable = null;

            while (cur != null) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) cur.getLastPathComponent();
                if (node.getUserObject() instanceof Editable) {
                    editable = (Editable) node.getUserObject();
                    break;
                }
                cur = cur.getParentPath();
            }

            if (editable == null) {
                continue;
            }

            if (e.isAddedPath(path)) {
                editorModel.addSelectedNode((DefaultMutableTreeNode) cur.getLastPathComponent(), editable);
            } else {
                editorModel.removeSelectedNode((DefaultMutableTreeNode) cur.getLastPathComponent(), editable);
            }
        }
    }
}
