package org.konkuk.client.controller;

import org.konkuk.client.component.VerifiedDegreePanel;
import org.konkuk.client.model.AppModel;
import org.konkuk.common.DegreeManager;
import org.konkuk.common.snapshot.DegreeSnapshot;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class VerifiedDegreePanelController {
    private final AppModel appModel = AppModel.getInstance();
    private final JTree tree;

    public VerifiedDegreePanelController(VerifiedDegreePanel panel) {
        tree = panel.getVerifiedDegreeTree();
        DegreeManager degreeManager = appModel.getDegreeManager();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        model.reload(root);

        degreeManager.getVerifiedDegreeMap().forEach((key, degreeList) -> addVerifiedDegreeList(degreeList));
    }

    private void addVerifiedDegreeList(List<DegreeSnapshot> degreesList) {
        String label = degreesList.get(0).criteria.degreeName;
        if (degreesList.size() > 1) {
            label += " 등 " + degreesList.size() + "건";
        }
        DefaultMutableTreeNode degreeListNode = new DefaultMutableTreeNode(label);
        degreesList.forEach(degree -> degreeListNode.add(new DefaultMutableTreeNode(degree.criteria.degreeName)));
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        model.insertNodeInto(degreeListNode, root, root.getChildCount());
    }
}
