package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class VerifierTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();

    private final DefaultMutableTreeNode root;

    public VerifierTreeModel() {
        super(new DefaultMutableTreeNode("/"));
        root = (DefaultMutableTreeNode) getRoot();

        if (appModel.getSelectedVerifier() != null) {
            update(appModel.getSelectedVerifier());
        }

        appModel.observe(AppModel.On.VERIFIER_SELECTED, selectedVerifier -> update((DegreeVerifier) selectedVerifier));
    }

    public void update(DegreeVerifier selectedVerifier) {
        if (selectedVerifier == null) {
            return;
        }
        root.removeAllChildren();
        root.add(new DefaultMutableTreeNode(selectedVerifier.degreeName));
        reload();
    }
}
