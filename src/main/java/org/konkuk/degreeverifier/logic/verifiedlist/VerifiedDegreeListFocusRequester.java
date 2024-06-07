package org.konkuk.degreeverifier.logic.verifiedlist;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.logic.verifiedlist.items.VerifiedDegreeListItem;

import javax.swing.*;

public class VerifiedDegreeListFocusRequester {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListFocusRequester(JList<VerifiedDegreeListItem> list) {
        appModel.observe(AppModel.On.COMMIT_STARTED, (student) -> list.requestFocus());
    }
}
