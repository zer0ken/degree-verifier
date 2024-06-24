package org.konkuk.degreeverifier.mainframe;

import com.formdev.flatlaf.extras.FlatInspector;
import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.common.components.DegreeVerifierFrame;
import org.konkuk.degreeverifier.common.components.statusbar.StatusBar;
import org.konkuk.degreeverifier.mainframe.components.SplitPanel;
import org.konkuk.degreeverifier.mainframe.components.menubar.MenuBar;
import org.konkuk.degreeverifier.ui.Themes;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_MAIN_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_MAIN_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.COMMITTING_TITLE;
import static org.konkuk.degreeverifier.ui.Strings.MAIN_FRAME_TITLE;

public class MainFrame extends DegreeVerifierFrame {
    public MainFrame() {
        super();
        setTitle(MAIN_FRAME_TITLE);
        setMinimumSize(MINIMUM_MAIN_FRAME_SIZE);
        setPreferredSize(PREFERRED_MAIN_FRAME_SIZE);
        setSize(PREFERRED_MAIN_FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        add(new SplitPanel());
        add(new StatusBar(), BorderLayout.SOUTH);

        AppModel.getInstance().observe(AppModel.On.COMMIT_UPDATED, (o) -> setTitle(String.format(COMMITTING_TITLE, o)));

        setVisible(true);
    }

    public void afterLaunched() {
        AppModel.getInstance().loadVerifier();
        AppModel.getInstance().loadStudentList();
        FlatInspector.install("ctrl shift alt X");
    }

    public static void main(String[] args) {
        Themes.setup();
        MainFrame mainFrame = new MainFrame();
        mainFrame.afterLaunched();
    }
}
