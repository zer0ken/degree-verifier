package org.konkuk.degreeverifier.editorframe.logic.verifiertree.node;

import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;

import javax.swing.tree.DefaultMutableTreeNode;

public class DegreeNode extends DefaultMutableTreeNode {
    private final EditableDegreeCriteria degree;

    private final DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode();
    private final DefaultMutableTreeNode validPeriodnode = new DefaultMutableTreeNode();
    private final RecursiveNode recursiveNode;

    public DegreeNode(EditableDegreeCriteria degree) {
        super(degree);
        this.degree = degree;
        recursiveNode = new RecursiveNode(degree.getEditableRecursiveCriteria());

        minimumCreditNode.setUserObject(degree.getValidCreditString());
        validPeriodnode.setUserObject(degree.getValidPeriodString());

        add(minimumCreditNode);
        add(validPeriodnode);
        add(recursiveNode);
    }

    public void update(VerifierTreeModel model) {
        setUserObject(degree);
        model.nodeChanged(this);

        minimumCreditNode.setUserObject(degree.getValidCreditString());
        model.nodeChanged(minimumCreditNode);

        validPeriodnode.setUserObject(degree.getValidPeriodString());
        model.nodeChanged(validPeriodnode);

        recursiveNode.update(model);
    }
}
