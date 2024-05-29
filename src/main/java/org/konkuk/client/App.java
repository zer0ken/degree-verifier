package org.konkuk.client;

import org.konkuk.client.component.*;
import org.konkuk.client.component.menus.MenuBar;
import org.konkuk.client.ui.Themes;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.konkuk.client.ui.Dimensions.DEFAULT_APP_SIZE;
import static org.konkuk.client.ui.Strings.APP_TITLE;

public class App extends JFrame {
    public App() {
        initIcons();
        setTitle(APP_TITLE);
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

    public static void main(String[] args) {
        Themes.init();

        new App();

        AppModel model = AppModel.getInstance();

        model.loadLectures();
        model.observe(AppModel.Observer.ON_LECTURE_LOADED, model::loadVerifiers);
        model.observe(AppModel.Observer.ON_VERIFIER_LOADED, model::verify);
    }

}
