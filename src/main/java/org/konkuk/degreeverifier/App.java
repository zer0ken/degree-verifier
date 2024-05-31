package org.konkuk.degreeverifier;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.components.*;
import org.konkuk.degreeverifier.components.menubar.MenuBar;
import org.konkuk.degreeverifier.components.statusbar.StatusPanel;

import javax.swing.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.konkuk.degreeverifier.ui.Dimensions.DEFAULT_APP_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_APP_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.APP_TITLE;
import static org.konkuk.degreeverifier.ui.Strings.COMMITTING_TITLE;

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
        FlatInspector.install( "ctrl shift alt X" );
    }

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();

        App app = new App();
        app.afterLaunched();
    }
}
