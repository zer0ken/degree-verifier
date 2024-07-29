package org.konkuk.degreeverifier.commitframe.components.verifiedlist.separators;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.SUFFICIENT_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.SUFFICIENT_DEGREES;

public class SufficientSeparatorCell extends LabeledSeparator {
    public SufficientSeparatorCell() {
        super(SUFFICIENT_DEGREES, SUFFICIENT_SEPARATOR_FOREGROUND, UIManager.getColor("List.background"));
    }
}
