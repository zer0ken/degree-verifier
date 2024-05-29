package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.common.verifier.DegreeVerifier;
import org.konkuk.common.verifier.RecursiveVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

import static org.konkuk.client.ui.Strings.VERIFIER_LOADING;

public class VerifierTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();
    private final DefaultMutableTreeNode rootNode;
    private final DefaultMutableTreeNode pendingNode;

    public VerifierTreeModel() {
        super(new DefaultMutableTreeNode("/"));

        rootNode = (DefaultMutableTreeNode) getRoot();
        pendingNode = new DefaultMutableTreeNode(VERIFIER_LOADING);

        appModel.observe(AppModel.Observer.ON_START_VERIFIER_LOAD, this::displayLoadingTree);
        appModel.observe(AppModel.Observer.ON_VERIFIER_LOADED, this::updateTree);
    }

    private void clearTree() {
        rootNode.removeAllChildren();
    }

    private void displayLoadingTree() {
        clearTree();
        rootNode.add(pendingNode);
        reload();
    }

    private void updateTree() {
        clearTree();

        List<DegreeVerifier> degreeVerifierList = appModel.getDegreeManager().getDegreeVerifiers();
        degreeVerifierList.forEach(degreeVerifier -> {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(degreeVerifier.toString());
            node.add(new DefaultMutableTreeNode("최소 학점: "+degreeVerifier.minimumCredit));
            rootNode.add(node);
            addRecursiveNodeToParent(node, degreeVerifier.getRecursiveVerifier());
        });
        reload();
    }

    private void addRecursiveNodeToParent(DefaultMutableTreeNode parent, RecursiveVerifier verifier) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(verifier.toString());
        parent.add(node);

        if (verifier.getLectureVerifier() == null) {
            verifier.getSubRecursiveVerifiers().forEach(
                    subVerifier -> addRecursiveNodeToParent(node, subVerifier)
            );
        }
    }
}
