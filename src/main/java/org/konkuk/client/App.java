package org.konkuk.client;

import org.konkuk.client.component.MenuBar;
import org.konkuk.client.component.VerifiedDegreePanel;
import org.konkuk.client.component.VerifierPanel;
import org.konkuk.client.model.AppModel;

import javax.swing.*;

import java.awt.*;

import static org.konkuk.client.ui.Dimensions.DEFAULT_APP_SIZE;
import static org.konkuk.client.ui.Strings.APP_TITLE;

public class App extends JFrame {
    public App() {
        setTitle(APP_TITLE);
        setSize(DEFAULT_APP_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(new VerifierPanel(), BorderLayout.WEST);

        add(new VerifiedDegreePanel(), BorderLayout.WEST);
        add(innerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new App();

        AppModel model = AppModel.getInstance();

        model.loadLectures();
        model.loadVerifiers();
    }
}
