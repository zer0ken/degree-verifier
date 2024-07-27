package org.konkuk.degreeverifier.commitframe.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.degreeverifier.business.DefaultPaths;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.konkuk.degreeverifier.ui.Strings.*;

public class ExportCommitAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public ExportCommitAction() {
        putValue(NAME, EXPORT_COMMIT);
        putValue(SHORT_DESCRIPTION, EXPORT_COMMIT + " (Ctrl+S)");
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("icons/save_icon.svg", getClass().getClassLoader()));
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
    }

    private AppModel.ExportMode getMode(ActionEvent e) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        panel.add(new JLabel("test1"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("test2"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("test3"), gbc);
        gbc.gridy++;

        int confirm = JOptionPane.showConfirmDialog((e != null ? (Component) e.getSource() : null),
                panel, EXPORT_COMMITTED_DEGREE, JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            return AppModel.ExportMode.NEW_ONLY;
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getMode(e));

        JFileChooser chooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                if (getSelectedFile().isDirectory() ||
                        (!getSelectedFile().exists() && getSelectedFile().getName().endsWith(".csv"))) {
                    super.approveSelection();
                } else if (getSelectedFile().getName().endsWith(".csv")) {
                    if (getSelectedFile().exists()) {
                        int confirm = JOptionPane.showConfirmDialog((e != null ? (Component) e.getSource() : null),
                                APPEND_EXPORT_MESSAGE, EXPORT_COMMITTED_DEGREE, JOptionPane.OK_CANCEL_OPTION);
                        if (confirm == JOptionPane.OK_OPTION) {
                            super.approveSelection();
                        }
                    } else {
                        super.approveSelection();
                    }
                } else if (!getSelectedFile().exists()) {
                    setSelectedFile(new File(getSelectedFile().getAbsolutePath() + ".csv"));
                    super.approveSelection();
                }
            }
        };
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(DefaultPaths.COMMIT_PATH));
        chooser.setSelectedFile(new File(DefaultPaths.COMMIT_PATH + "\\"
                + new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초").format(new Date()) + ".csv"));
        chooser.setDialogTitle(EXPORT_COMMIT_DIALOG_TITLE);
        int result = chooser.showSaveDialog(e != null ? (Component) e.getSource() : null);
        if (result == JFileChooser.APPROVE_OPTION) {
            appModel.export(chooser.getSelectedFile());

            try {
                Desktop.getDesktop().open(chooser.getCurrentDirectory());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
