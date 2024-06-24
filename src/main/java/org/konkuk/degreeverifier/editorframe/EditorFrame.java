package org.konkuk.degreeverifier.editorframe;

import org.konkuk.degreeverifier.common.components.DegreeVerifierFrame;
import org.konkuk.degreeverifier.editorframe.components.SplitPanel;
import org.konkuk.degreeverifier.editorframe.components.applypanel.ApplyPanel;

import java.awt.*;

import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Dimensions.PREFERRED_EDIT_FRAME_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.EDIT_FRAME_TITLE;

public class EditorFrame extends DegreeVerifierFrame {
    private static final EditorFrame instance = new EditorFrame();

    public static EditorFrame getInstance() {
        return instance;
    }
    
    protected EditorFrame() {
        super();

        setTitle(EDIT_FRAME_TITLE);
        setMinimumSize(MINIMUM_EDIT_FRAME_SIZE);
        setPreferredSize(PREFERRED_EDIT_FRAME_SIZE);
        setSize(PREFERRED_EDIT_FRAME_SIZE);
        setLayout(new BorderLayout());

        add(new SplitPanel());
        add(new ApplyPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            setState(Frame.NORMAL);
        }
    }
}
