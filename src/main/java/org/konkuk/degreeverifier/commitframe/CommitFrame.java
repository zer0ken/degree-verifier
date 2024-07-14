package org.konkuk.degreeverifier.commitframe;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.components.SplitPanel;
import org.konkuk.degreeverifier.commitframe.components.menubar.MenuBar;
import org.konkuk.degreeverifier.common.components.DegreeVerifierFrame;
import org.konkuk.degreeverifier.common.components.statusbar.StatusBar;

import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_COMMIT_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_COMMIT_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.APP_TITLE;
import static org.konkuk.degreeverifier.ui.Strings.COMMITTING_TITLE;

public class CommitFrame extends DegreeVerifierFrame {
    private static final CommitFrame instance = new CommitFrame();

    public static CommitFrame getInstance() {
        return instance;
    }

    protected CommitFrame() {
        super();
        setTitle(APP_TITLE);
        setMinimumSize(MINIMUM_COMMIT_FRAME_SIZE);
        setPreferredSize(PREFERRED_COMMIT_FRAME_SIZE);
        setSize(PREFERRED_COMMIT_FRAME_SIZE);
        setLayout(new BorderLayout());

        setJMenuBar(new MenuBar());

        add(new SplitPanel());
        add(new StatusBar(), BorderLayout.SOUTH);

        AppModel.getInstance().observe(AppModel.On.COMMIT_UPDATED, (o) -> setTitle(String.format(COMMITTING_TITLE, o)));
    }
}
