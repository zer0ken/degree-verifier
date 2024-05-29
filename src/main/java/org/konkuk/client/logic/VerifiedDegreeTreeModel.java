package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.common.snapshot.DegreeSnapshot;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.util.List;
import java.util.Map;

import static org.konkuk.client.ui.Strings.VERIFING;

public class VerifiedDegreeTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();
    private final DefaultMutableTreeNode rootNode;
    private final DefaultMutableTreeNode pendingNode;

    public VerifiedDegreeTreeModel() {
        super(new DefaultMutableTreeNode("/"));

        rootNode = (DefaultMutableTreeNode) getRoot();
        pendingNode = new DefaultMutableTreeNode(VERIFING);

        appModel.observe(AppModel.Observer.ON_START_VERIFY, this::displayPendingTree);
        appModel.observe(AppModel.Observer.ON_VERIFIED, this::updateTree);
    }

    public void clearTree() {
        rootNode.removeAllChildren();
        reload();
    }

    public void displayPendingTree() {
        clearTree();
        insertNodeInto(pendingNode, rootNode, rootNode.getChildCount());
        reload();
    }

    public void updateTree() {
        clearTree();

        Map<String, List<DegreeSnapshot>> verifiedDegreeMap = appModel.getDegreeManager().getVerifiedDegreeMap();

        verifiedDegreeMap.forEach((key, degreeSnapshotList) -> {
            String label = degreeSnapshotList.get(0).criteria.degreeName;
            if (degreeSnapshotList.size() > 1) {
                label += " 등 " + degreeSnapshotList.size() + "건";
            }
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(label);
            insertNodeInto(node, rootNode, rootNode.getChildCount());

            degreeSnapshotList.forEach(degreeSnapshot -> {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(degreeSnapshot.criteria.degreeName);
                node.add(child);
            });
        });

        reload();
    }
}
