package org.konkuk.degreeverifier.launcherframe;

import org.konkuk.degreeverifier.common.components.DegreeVerifierFrame;
import org.konkuk.degreeverifier.common.components.statusbar.StatusBar;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_LAUNCHER_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFFERED_LAUNCHER_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.APP_TITLE;

public class LauncherFrame extends DegreeVerifierFrame {
    public LauncherFrame() {
        super();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_LAUNCHER_FRAME_SIZE);
        setPreferredSize(PREFFERED_LAUNCHER_FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(new ActionPanel());
        bottom.add(new StatusBar(), BorderLayout.SOUTH);

        add(new LauncherPanel());
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
