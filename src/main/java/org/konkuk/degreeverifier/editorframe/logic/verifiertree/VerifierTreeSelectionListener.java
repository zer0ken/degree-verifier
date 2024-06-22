package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class VerifierTreeSelectionListener implements TreeSelectionListener {
    private final EditorModel editorModel = EditorModel.getInstance();

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        System.out.println("valueChanged");
        for (TreePath path : e.getPaths()) {
            TreePath cur = path;
            RecursiveCriteria criteria = null;

            while (cur != null) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) cur.getLastPathComponent();
                if (node.getUserObject() instanceof RecursiveCriteria) {
                    criteria = (RecursiveCriteria) node.getUserObject();
                    break;
                }
                cur = cur.getParentPath();
            }

            if (criteria == null) {
                continue;
            }

            if (e.isAddedPath(path)) {
                editorModel.addSelectedNode(criteria);
            } else {
                editorModel.removeSelectedNode(criteria);
            }
        }
        for (RecursiveCriteria selectedNode : editorModel.getSelectedNodes()) {
            System.out.println(selectedNode);
        }
    }
}
