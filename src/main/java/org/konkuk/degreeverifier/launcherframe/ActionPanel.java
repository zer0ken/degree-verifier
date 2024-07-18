package org.konkuk.degreeverifier.launcherframe;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.CommitFrame;
import org.konkuk.degreeverifier.editorframe.EditorFrame;

import javax.swing.*;
import java.awt.*;

import static org.konkuk.degreeverifier.ui.Borders.ACTION_PANEL_BORDER;

public class ActionPanel extends JPanel {
    public ActionPanel() {
        setLayout(new BorderLayout());
        add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
        inner.setBorder(ACTION_PANEL_BORDER);
        AppModel appModel = AppModel.getInstance();

        JButton showEditorButton = new JButton("검사 기준 편집하기");
        showEditorButton.addActionListener(e ->
                EditorFrame.getInstance().setVisible(true)
        );
        showEditorButton.setEnabled(appModel.isVerifierLoaded());
        appModel.observe(AppModel.On.VERIFIER_LOADED, (unused) -> showEditorButton.setEnabled(true));

        JButton showCommitButton = new JButton("직접 검토하기");
        showCommitButton.addActionListener(e ->
                CommitFrame.getInstance().setVisible(true)
        );

        JButton autoCommitButton = new JButton("자동 검사");
        autoCommitButton.addActionListener(e -> {
            AppModel model = AppModel.getInstance();
            model.commitAllStudentAutomaticallyAndExport(e);
        });
        autoCommitButton.setEnabled(appModel.isVerifierLoaded() && appModel.isTranscriptLoaded());
        AppModel.getInstance().observe(AppModel.On.VERIFIER_LOADED, (unused) ->
                autoCommitButton.setEnabled(appModel.isVerifierLoaded() && appModel.isTranscriptLoaded())
        );
        AppModel.getInstance().observe(AppModel.On.TRANSCRIPT_LOADED, (unused) ->
                autoCommitButton.setEnabled(appModel.isVerifierLoaded() && appModel.isTranscriptLoaded())
        );

        inner.add(Box.createGlue());
        inner.add(showEditorButton);
        inner.add(Box.createHorizontalStrut(16));
        inner.add(showCommitButton);
        inner.add(Box.createGlue());
        inner.add(autoCommitButton);
        inner.add(Box.createGlue());

        add(inner);
    }
}
