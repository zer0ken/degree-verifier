package org.konkuk.degreeverifier.launcherframe;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.CommitFrame;
import org.konkuk.degreeverifier.commitframe.actions.ExportCommitAction;
import org.konkuk.degreeverifier.editorframe.EditorFrame;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    public ActionPanel() {
        setLayout(new BorderLayout());
        add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);

        JPanel inner = new JPanel();

        JButton showEditorButton = new JButton("검사 기준 편집하기");
        showEditorButton.addActionListener(e ->
                EditorFrame.getInstance().setVisible(true)
        );

        JButton showCommitButton = new JButton("직접 검토하기");
        showCommitButton.addActionListener(e ->
                CommitFrame.getInstance().setVisible(true)
        );

        JButton autoCommitButton = new JButton("자동 검사");
        autoCommitButton.addActionListener(e -> {
            AppModel model = AppModel.getInstance();
            model.commitAllStudentAutomatically();
            new ExportCommitAction().actionPerformed(null);
        });

        inner.add(showEditorButton);
        inner.add(showCommitButton);
        inner.add(autoCommitButton);

        add(inner);
    }
}
