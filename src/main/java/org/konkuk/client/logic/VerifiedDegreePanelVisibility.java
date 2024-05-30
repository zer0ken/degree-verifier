package org.konkuk.client.logic;

import org.konkuk.client.AppModel;
import org.konkuk.client.component.VerifiedDegreePanel;

public class VerifiedDegreePanelVisibility {
    public VerifiedDegreePanelVisibility(VerifiedDegreePanel panel) {
        panel.setVisible(false);

        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.ObserveOf.ON_START_VERIFY, () -> panel.setVisible(true));
    }
}
