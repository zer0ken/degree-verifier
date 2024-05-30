package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.common.verify.verifier.DegreeVerifier;
import org.konkuk.common.verify.verifier.RecursiveVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

import static org.konkuk.client.ui.Strings.VERIFIER_LOADING_MESSAGE;

public class VerifierTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();
    private final DefaultMutableTreeNode rootNode;
    private final DefaultMutableTreeNode pendingNode;

    public VerifierTreeModel() {
        super(new DefaultMutableTreeNode("/"));

        rootNode = (DefaultMutableTreeNode) getRoot();
        pendingNode = new DefaultMutableTreeNode(VERIFIER_LOADING_MESSAGE);

        appModel.observe(AppModel.ObserveOf.ON_START_VERIFIER_LOAD, this::displayLoadingTree);
        appModel.observe(AppModel.ObserveOf.ON_VERIFIER_LOADED, this::updateTree);
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

        List<DegreeVerifier> degreeVerifierList = appModel.getVerifier().getDegreeVerifiers();
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
