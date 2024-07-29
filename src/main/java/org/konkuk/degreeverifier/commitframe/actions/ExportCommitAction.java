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

    private AppModel.ExportMode inputMode(ActionEvent e) {
        JRadioButton validOldButton = new JRadioButton(EXPORT_COMMIT_VALIDATE_OLD);
        JRadioButton newOnlyButton = new JRadioButton(EXPORT_COMMIT_NEW_ONLY);
        JRadioButton newAndOldButton = new JRadioButton(EXPORT_COMMIT_NEW_AND_OLD);
        validOldButton.setToolTipText(EXPORT_COMMIT_VALIDATE_OLD_TOOLTIP);
        newOnlyButton.setToolTipText(EXPORT_COMMIT_NEW_ONLY_TOOLTIP);
        newAndOldButton.setToolTipText(EXPORT_COMMIT_NEW_AND_OLD_TOOLTIP);

        switch (appModel.getExportMode()){
            case NEW_AND_OLD:
                newAndOldButton.setSelected(true);
                break;
            case NEW_ONLY:
                newOnlyButton.setSelected(true);
                break;
            case VALIDATE_OLD:
                validOldButton.setSelected(true);
                break;
        }

        validOldButton.addActionListener(e_ -> appModel.setExportMode(AppModel.ExportMode.VALIDATE_OLD));
        newOnlyButton.addActionListener(e_ -> appModel.setExportMode(AppModel.ExportMode.NEW_ONLY));
        newAndOldButton.addActionListener(e_ -> appModel.setExportMode(AppModel.ExportMode.NEW_AND_OLD));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(validOldButton);
        buttonGroup.add(newOnlyButton);
        buttonGroup.add(newAndOldButton);

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.add(new JLabel("파일 출력 설정을 선택하세요."));
        inner.add(Box.createVerticalStrut(12));
        inner.add(validOldButton);
        inner.add(newOnlyButton);
        inner.add(newAndOldButton);

        int confirm = JOptionPane.showConfirmDialog((e != null ? (Component) e.getSource() : null),
                inner, EXPORT_COMMITTED_DEGREE, JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            return appModel.getExportMode();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppModel.ExportMode mode=  inputMode(e);
        if (mode == null){
            return;
        }
        ExportCommitAction.defaultActionPerformed(e, mode);
    }

    public static void defaultActionPerformed(ActionEvent e, AppModel.ExportMode mode) {
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
            AppModel.getInstance().setExportMode(mode);
            AppModel.getInstance().export(chooser.getSelectedFile());
            try {
                Desktop.getDesktop().open(chooser.getCurrentDirectory());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
