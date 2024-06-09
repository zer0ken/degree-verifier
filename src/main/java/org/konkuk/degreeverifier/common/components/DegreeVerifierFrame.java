package org.konkuk.degreeverifier.common.components;

import org.konkuk.degreeverifier.mainframe.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DegreeVerifierFrame extends JFrame {
    public DegreeVerifierFrame() {
        initIcons();
    }

    private void initIcons() {
        String[] iconNames = {
                "/appicons/app_icon_16.png",
                "/appicons/app_icon_24.png",
                "/appicons/app_icon_36.png",
                "/appicons/app_icon_48.png",
                "/appicons/app_icon_64.png",
        };
        List<Image> icons = new ArrayList<>();
        for (String iconName : iconNames) {
            URL resource = MainFrame.class.getResource(iconName);
            if (resource == null) {
                return;
            }
            icons.add((new ImageIcon(resource)).getImage());
        }
        setIconImages(icons);
    }
}
