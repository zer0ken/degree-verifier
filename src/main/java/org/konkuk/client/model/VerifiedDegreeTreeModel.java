package org.konkuk.client.model;

import org.konkuk.common.snapshot.DegreeSnapshot;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.util.List;
import java.util.Map;

import static org.konkuk.client.ui.Strings.VERIFING;

public class VerifiedDegreeTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();
    private final DefaultMutableTreeNode pendingNode;

    public VerifiedDegreeTreeModel() {
        super(new DefaultMutableTreeNode("/"));

        pendingNode = new DefaultMutableTreeNode(VERIFING);

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

        Map<String, List<DegreeSnapshot>> verifiedDegreeMap = appModel.getDegreeManager().getVerifiedDegreeMap();

        verifiedDegreeMap.forEach((key, degreeSnapshotList) -> {
            String label = degreeSnapshotList.get(0).criteria.degreeName;
            if (degreeSnapshotList.size() > 1) {
                label += " 등 " + degreeSnapshotList.size() + "건";
            }
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(label);

            degreeSnapshotList.forEach(degreeSnapshot -> {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(degreeSnapshot.criteria.degreeName);
                node.add(child);
            });

            insertNodeInto(node, (MutableTreeNode) getRoot(), getChildCount(getRoot()));
        });

        reload();
    }
}
