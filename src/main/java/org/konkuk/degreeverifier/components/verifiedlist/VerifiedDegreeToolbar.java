package org.konkuk.degreeverifier.components.verifiedlist;

import org.konkuk.degreeverifier.actions.CommitDegreeAction;
import org.konkuk.degreeverifier.actions.CommitRecommendedDegreesAction;
import org.konkuk.degreeverifier.components.TitledToolbar2;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIED_DEGREE_LIST;

public class VerifiedDegreeToolbar extends TitledToolbar2 {
    public VerifiedDegreeToolbar() {
        super(VERIFIED_DEGREE_LIST);

        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
    }
}
