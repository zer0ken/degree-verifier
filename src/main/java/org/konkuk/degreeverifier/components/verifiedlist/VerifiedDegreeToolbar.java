package org.konkuk.degreeverifier.components.verifiedlist;

import org.konkuk.degreeverifier.actions.*;
import org.konkuk.degreeverifier.components.TitledToolbar;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIED_DEGREE_LIST;

public class VerifiedDegreeToolbar extends TitledToolbar {
    public VerifiedDegreeToolbar() {
        super(VERIFIED_DEGREE_LIST);

        add(new CommitDegreeAction());
        add(new CommitRecommendedDegreesAction());
    }
}
