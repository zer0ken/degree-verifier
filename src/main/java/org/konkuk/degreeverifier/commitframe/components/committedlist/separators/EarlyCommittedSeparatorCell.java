package org.konkuk.degreeverifier.commitframe.components.committedlist.separators;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.EARLY_COMMITTED_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.EARLY_COMMITTED_DEGREE;

public class EarlyCommittedSeparatorCell extends LabeledSeparator {
    public EarlyCommittedSeparatorCell() {
        super(EARLY_COMMITTED_DEGREE, EARLY_COMMITTED_SEPARATOR_FOREGROUND, UIManager.getColor("List.background"));
    }
}
