package org.konkuk.degreeverifier.mainframe.components.verifiedlist.separators;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.NOT_VERIFIED_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.NOT_VERIFIED_DEGREES;

public class NotVerifiedSeparatorCell extends LabeledSeparator {
    public NotVerifiedSeparatorCell() {
        super(NOT_VERIFIED_DEGREES, NOT_VERIFIED_SEPARATOR_FOREGROUND, UIManager.getColor("List.background"));
    }
}
