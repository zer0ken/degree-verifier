package org.konkuk.degreeverifier.mainframe.components.verifiedlist;

import org.konkuk.degreeverifier.common.components.InnerTitledToolbar;
import org.konkuk.degreeverifier.mainframe.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.mainframe.actions.CommitRecommendedDegreesAction;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIED_DEGREE_LIST;

public class VerifiedDegreeToolbar extends InnerTitledToolbar {
    public VerifiedDegreeToolbar() {
        super(VERIFIED_DEGREE_LIST);

        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
    }
}
