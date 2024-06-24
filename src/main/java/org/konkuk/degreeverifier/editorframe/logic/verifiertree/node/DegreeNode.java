package org.konkuk.degreeverifier.editorframe.logic.verifiertree.node;

import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;

import javax.swing.tree.DefaultMutableTreeNode;

public class DegreeNode extends DefaultMutableTreeNode {
    private final EditableDegreeCriteria degree;

    private final DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode();
    private final RecursiveNode recursiveNode;

    public DegreeNode(EditableDegreeCriteria degree) {
        super(degree);
        this.degree = degree;
        recursiveNode = new RecursiveNode(degree.getEditableRecursiveCriteria());

        add(minimumCreditNode);
        add(new RecursiveNode(degree.getEditableRecursiveCriteria()));
    }

    public void update(VerifierTreeModel model) {
        minimumCreditNode.setUserObject("필요 학점: " + degree.minimumCredit + " ~");
        model.nodeChanged(minimumCreditNode);
//        recursiveNode.update(model);
    }
}
