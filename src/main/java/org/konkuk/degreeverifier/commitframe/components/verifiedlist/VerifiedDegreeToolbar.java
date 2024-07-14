package org.konkuk.degreeverifier.commitframe.components.verifiedlist;

import org.konkuk.degreeverifier.commitframe.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.commitframe.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.common.components.InnerTitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIED_DEGREE_LIST;

public class VerifiedDegreeToolbar extends InnerTitledToolbar {
    public VerifiedDegreeToolbar() {
        super(VERIFIED_DEGREE_LIST);

        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
    }
}
