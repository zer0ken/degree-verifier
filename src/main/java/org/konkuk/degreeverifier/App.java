package org.konkuk.degreeverifier;

import org.konkuk.degreeverifier.launcherframe.LauncherFrame;
import org.konkuk.degreeverifier.ui.Themes;

public class App {
    public static void main(String[] args) {
        Themes.setup();

        new LauncherFrame();
    }
}
