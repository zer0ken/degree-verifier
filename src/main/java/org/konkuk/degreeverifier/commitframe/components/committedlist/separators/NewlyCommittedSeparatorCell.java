package org.konkuk.degreeverifier.commitframe.components.committedlist.separators;

import org.konkuk.degreeverifier.common.components.LabeledSeparator;

import javax.swing.*;

import static org.konkuk.degreeverifier.ui.Colors.NEWLY_COMMITTED_SEPARATOR_FOREGROUND;
import static org.konkuk.degreeverifier.ui.Strings.NEWLY_COMMITTED_DEGREE;

public class NewlyCommittedSeparatorCell extends LabeledSeparator {
    public NewlyCommittedSeparatorCell() {
        super(NEWLY_COMMITTED_DEGREE, NEWLY_COMMITTED_SEPARATOR_FOREGROUND, UIManager.getColor("List.background"));
    }
}
