package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.client.component.VerifiedDegreePanel;

public class VerifiedDegreePanelPresenter {
    public VerifiedDegreePanelPresenter(VerifiedDegreePanel panel) {
        panel.setVisible(false);

        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.Observer.ON_START_VERIFY, () -> panel.setVisible(true));
    }
}
