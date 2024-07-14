package org.konkuk.degreeverifier;

import com.formdev.flatlaf.extras.FlatInspector;
import org.konkuk.degreeverifier.launcherframe.LauncherFrame;
import org.konkuk.degreeverifier.ui.Themes;

public class App {
    public static void main(String[] args) {
        Themes.setup();

        new LauncherFrame();

        // TODO: 2024-07-15 to debug. remove this after test.
        FlatInspector.install("ctrl shift alt X");
    }
}
