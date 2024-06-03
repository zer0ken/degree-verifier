package org.konkuk.degreeverifier;

import com.formdev.flatlaf.extras.FlatInspector;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.components.SplitPanel;
import org.konkuk.degreeverifier.components.menubar.MenuBar;
import org.konkuk.degreeverifier.components.statusbar.StatusBar;
import org.konkuk.degreeverifier.ui.Themes;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_APP_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_APP_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.APP_TITLE;
import static org.konkuk.degreeverifier.ui.Strings.COMMITTING_TITLE;

public class App extends JFrame {
    public App() {
        initIcons();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_APP_SIZE);
        setPreferredSize(PREFERRED_APP_SIZE);
        setSize(PREFERRED_APP_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        AppModel.getInstance().observe(AppModel.ObserveOn.ON_COMMIT_UPDATED, (o) -> {
            setTitle(String.format(COMMITTING_TITLE, o));
        });

        setJMenuBar(new MenuBar());

        add(new SplitPanel());
        add(new StatusBar(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initIcons() {
        String[] iconNames = {
                "/icons/app_icon_16.png",
                "/icons/app_icon_24.png",
                "/icons/app_icon_36.png",
                "/icons/app_icon_48.png",
                "/icons/app_icon_64.png",
        };
        List<Image> icons = new ArrayList<>();
        for (String iconName : iconNames) {
            URL resource = App.class.getResource(iconName);
            if (resource == null) {
                return;
            }
            icons.add((new ImageIcon(resource)).getImage());
        }
        setIconImages(icons);
    }

    public void afterLaunched() {
        AppModel.getInstance().loadVerifier();
        AppModel.getInstance().loadStudentList();
        FlatInspector.install("ctrl shift alt X");
    }

    public static void main(String[] args) {
        Themes.setup();
        App app = new App();
        app.afterLaunched();
    }
}
