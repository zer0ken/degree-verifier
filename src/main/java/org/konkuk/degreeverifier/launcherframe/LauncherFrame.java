package org.konkuk.degreeverifier.launcherframe;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_LAUNCHER_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFFERED_LAUNCHER_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.APP_TITLE;

public class LauncherFrame extends JFrame {
    private final GridBagConstraints gbc = new GridBagConstraints();

    public LauncherFrame() {
        super();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_LAUNCHER_FRAME_SIZE);
        setPreferredSize(PREFFERED_LAUNCHER_FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new LauncherPanel());
        add(new ActionPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }
}
