package org.konkuk.degreeverifier.commitframe.logic.verifiedlist;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.logic.verifiedlist.items.ListItem;

import javax.swing.*;

public class VerifiedDegreeListFocusRequester {
    private final AppModel appModel = AppModel.getInstance();

    public VerifiedDegreeListFocusRequester(JList<ListItem> list) {
        appModel.observe(AppModel.On.COMMIT_STARTED, (student) -> list.requestFocus());
    }
}
