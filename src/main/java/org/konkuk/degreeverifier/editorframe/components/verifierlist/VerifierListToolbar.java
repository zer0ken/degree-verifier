package org.konkuk.degreeverifier.editorframe.components.verifierlist;

import org.konkuk.degreeverifier.common.actions.OpenVerifierDirectoryAction;
import org.konkuk.degreeverifier.common.components.TitledToolbar;
import org.konkuk.degreeverifier.editorframe.actions.CreateDegreeVerifierAction;
import org.konkuk.degreeverifier.editorframe.actions.RemoveDegreeVerifierAction;

import static org.konkuk.degreeverifier.ui.Strings.VERIFIER_LIST;

public class VerifierListToolbar extends TitledToolbar {
    public VerifierListToolbar() {
        super(VERIFIER_LIST);
        add(new CreateDegreeVerifierAction());
        add(new RemoveDegreeVerifierAction());
        addSeparator();
        add(new OpenVerifierDirectoryAction());
    }
}
