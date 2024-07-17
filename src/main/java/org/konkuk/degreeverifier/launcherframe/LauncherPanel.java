package org.konkuk.degreeverifier.launcherframe;

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

        initGridBagConstraints();
        addRow("검사 기준 불러오기*:", new JButton(new LoadVerifierAction()));
        addRow("성적표 불러오기*:", new JButton(new LoadTranscriptAction()));
        addRow("동일 교과 목록 불러오기:", new JButton(new LoadAliasesAction()));
        addRow("기존 학위 불러오기:", new JButton(new LoadCommitAction()));
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
