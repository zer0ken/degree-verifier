package org.konkuk.client;

import org.konkuk.client.component.*;
import org.konkuk.client.component.menus.MenuBar;
import org.konkuk.client.component.statusbar.StatusPanel;
import org.konkuk.client.ui.Themes;

import javax.swing.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.konkuk.client.ui.Dimensions.DEFAULT_APP_SIZE;
import static org.konkuk.client.ui.Dimensions.MINIMUM_APP_SIZE;
import static org.konkuk.client.ui.Strings.APP_TITLE;

public class App extends JFrame {
    public App() {
        initIcons();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_APP_SIZE);
        setSize(DEFAULT_APP_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        add(new CommitPanel());
//        add(new JLabel("✔➖❌"), BorderLayout.SOUTH);
        add(new StatusPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initIcons() {
        String[] iconNames = {
                "icons/icon_16.png",
                "icons/icon_24.png",
                "icons/icon_36.png",
                "icons/icon_48.png",
                "icons/icon_64.png",
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
        AppModel.getInstance().loadVerifiers();
        AppModel.getInstance().loadStudents();
    }

    public static void main(String[] args) {
        Themes.init();

        App app = new App();
//        app.afterLaunched();
    }
}
