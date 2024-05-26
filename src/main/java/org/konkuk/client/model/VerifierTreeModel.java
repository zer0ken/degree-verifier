package org.konkuk.client.model;

import org.konkuk.common.snapshot.DegreeSnapshot;
import org.konkuk.common.verifier.DegreeVerifier;
import org.konkuk.common.verifier.LectureVerifier;
import org.konkuk.common.verifier.RecursiveVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import java.util.List;
import java.util.Map;

import static org.konkuk.client.ui.Strings.VERIFIER_LOADING;

public class VerifierTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();
    private final DefaultMutableTreeNode pendingNode;

    public VerifierTreeModel() {
        super(new DefaultMutableTreeNode("/"));

        pendingNode = new DefaultMutableTreeNode(VERIFIER_LOADING);

        appModel.observe(AppModel.Observer.ON_START_VERIFY, this::clearTree);
        appModel.observe(AppModel.Observer.ON_VERIFIED, this::updateTree);
    }

    public void clearTree() {
        ((DefaultMutableTreeNode) getRoot()).removeAllChildren();
        insertNodeInto(pendingNode, (MutableTreeNode) getRoot(), getChildCount(getRoot()));

        reload();
    }

    public void updateTree() {
        removeNodeFromParent(pendingNode);

        List<DegreeVerifier> degreeVerifierList = appModel.getDegreeManager().getDegreeVerifiers();

        degreeVerifierList.forEach(degreeVerifier -> {
            DefaultMutableTreeNode degreeVerifierNode = new DefaultMutableTreeNode(degreeVerifier.degreeName);
            addRecursiveNodeToParent(degreeVerifierNode, degreeVerifier.getRecursiveVerifier());
        });

        reload();
    }

    private void addRecursiveNodeToParent(DefaultMutableTreeNode parent, RecursiveVerifier verifier) {
        if (verifier.lectureCriteria != null) {
            addLectureNodeToParent(parent, verifier.getLectureVerifier());
            return;
        }
        String label = verifier.
        DefaultMutableTreeNode node = new DefaultMutableTreeNode()
        verifier.getSubRecursiveVerifiers().forEach(recursiveVerifier -> {
            addRecursiveNodeToParent(parent, recursiveVerifier);
        });
    }

    private void addLectureNodeToParent(DefaultMutableTreeNode parent, LectureVerifier verifier) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("강의: " + verifier.lectureName);
        insertNodeInto(node, parent, parent.getChildCount());
    }
}
