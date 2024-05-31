package org.konkuk.client;

import com.formdev.flatlaf.extras.FlatInspector;
import org.konkuk.client.components.*;
import org.konkuk.client.components.menubar.MenuBar;
import org.konkuk.client.components.statusbar.StatusPanel;
import org.konkuk.client.ui.Themes;

import javax.swing.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.konkuk.client.ui.Dimensions.DEFAULT_APP_SIZE;
import static org.konkuk.client.ui.Dimensions.MINIMUM_APP_SIZE;
import static org.konkuk.client.ui.Strings.APP_TITLE;
import static org.konkuk.client.ui.Strings.COMMITTING_TITLE;

public class App extends JFrame {
    public App() {
        initIcons();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_APP_SIZE);
        setSize(DEFAULT_APP_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        AppModel.getInstance().observe(AppModel.ObserveOf.ON_COMMIT_STARTED, (o) -> {
            setTitle(String.format(COMMITTING_TITLE, o));
        });

        setJMenuBar(new MenuBar());

        add(new CommittingPanel());
//        add(new JLabel("✔➖❌"), BorderLayout.SOUTH);
        add(new StatusPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initIcons() {
        String[] iconNames = {
                "/org/konkuk/icons/app_icon_16.png",
                "/org/konkuk/icons/app_icon_24.png",
                "/org/konkuk/icons/app_icon_36.png",
                "/org/konkuk/icons/app_icon_48.png",
                "/org/konkuk/icons/app_icon_64.png",
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
        FlatInspector.install( "ctrl shift alt X" );
    }

    public static void main(String[] args) {
        Themes.init();

        App app = new App();
        app.afterLaunched();
    }
}
