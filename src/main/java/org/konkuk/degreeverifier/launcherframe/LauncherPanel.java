package org.konkuk.degreeverifier.launcherframe;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.commitframe.actions.LoadAliasesAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadCommitAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadTranscriptAction;
import org.konkuk.degreeverifier.commitframe.actions.LoadVerifierAction;

import javax.swing.*;
import java.awt.*;

public class LauncherPanel extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();

    public LauncherPanel() {
        setLayout(new GridBagLayout());

        JButton b1 = new JButton(new LoadVerifierAction());
        JButton b2 = new JButton(new LoadTranscriptAction());
        JButton b3 = new JButton(new LoadAliasesAction());
        JButton b4 = new JButton(new LoadCommitAction());

        AppModel appModel = AppModel.getInstance();
        appModel.observe(AppModel.On.VERIFIER_LOADED, unused -> {
            String path = appModel.getVerifierDirectory().getAbsolutePath();
            b1.setText(path);
            b1.setToolTipText(path);
        });
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, unused -> {
            String path = appModel.getTranscriptFile().getAbsolutePath();
            b2.setText(path);
            b2.setToolTipText(path);
        });
        appModel.observe(AppModel.On.ALIASES_LOADED, unused -> {
            String path = appModel.getAliasesFile().getAbsolutePath();
            b3.setText(path);
            b3.setToolTipText(path);
        });
        appModel.observe(AppModel.On.COMMIT_LOADED, unused -> {
            String path = appModel.getEarlyCommitFile().getAbsolutePath();
            b4.setText(path);
            b4.setToolTipText(path);
        });

        initGridBagConstraints();
        addRow("검사 기준 불러오기*:", b1);
        addRow("성적표 불러오기*:", b2);
        addRow("동일 교과 목록 불러오기:", b3);
        addRow("기존 학위 불러오기:", b4);
    }

    private void initGridBagConstraints() {
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 8, 4, 8);
    }

    private void addRow(String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(4, 40, 4, 8);
        add(new JLabel(label), gbc);

        gbc.gridx++;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 8, 4, 40);
        add(component, gbc);
    }
}
