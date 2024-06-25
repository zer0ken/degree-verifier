package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.components.verifiertree.VerifierTree;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.node.DegreeNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.LinkedList;

public class VerifierTreeModel extends DefaultTreeModel {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final DefaultMutableTreeNode root;
    private DegreeNode degreeNode;
    private final VerifierTree tree;

    public VerifierTreeModel(VerifierTree tree) {
        super(new DefaultMutableTreeNode("/"));
        root = (DefaultMutableTreeNode) getRoot();
        this.tree = tree;

        if (editorModel.getSelectedDegrees().size() == 1) {
            initTree(editorModel.getSelectedDegree());
        }

        editorModel.observe(EditorModel.On.DEGREE_SELECTED, _selectedDegrees -> {
            LinkedList<EditableDegreeCriteria> selectedDegrees = (LinkedList<EditableDegreeCriteria>) _selectedDegrees;
            if (selectedDegrees.size() == 1) {
                initTree(selectedDegrees.get(0));
            } else {
                initTree(null);
            }
        });
        editorModel.observe(EditorModel.On.DEGREE_UPDATED, unused -> updateTree());
    }

    private void initTree(EditableDegreeCriteria selectedDegree) {
        root.removeAllChildren();
        if (selectedDegree == null) {
            reload();
            return;
        }
        degreeNode = new DegreeNode(selectedDegree);
        root.add(degreeNode);
        reload();
        tree.updateFoldingState();
    }

    private void updateTree() {
        degreeNode.update(this);
    }
}
