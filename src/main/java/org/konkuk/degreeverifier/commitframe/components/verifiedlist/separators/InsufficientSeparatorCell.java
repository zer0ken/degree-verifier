package org.konkuk.degreeverifier.commitframe.components.verifiedlist.separators;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.INSUFFICIENT_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.INSUFFICIENT_DEGREES;

public class InsufficientSeparatorCell extends LabeledSeparator {
    public InsufficientSeparatorCell() {
        super(INSUFFICIENT_DEGREES, INSUFFICIENT_SEPARATOR_FOREGROUND, UIManager.getColor("List.background"));
    }
}
