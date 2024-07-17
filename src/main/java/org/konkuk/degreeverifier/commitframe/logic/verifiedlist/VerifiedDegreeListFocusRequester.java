package org.konkuk.degreeverifier.commitframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

import javax.swing.*;

public class VerifiedDegreeListFocusRequester {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListFocusRequester(JList<VerifierListItem> list) {
        appModel.observe(AppModel.On.COMMIT_STARTED, (student) -> list.requestFocus());
    }
}
