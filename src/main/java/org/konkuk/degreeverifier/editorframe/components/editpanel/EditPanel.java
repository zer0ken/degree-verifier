package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.common.components.IndentedGridCell;
import org.konkuk.degreeverifier.editorframe.logic.editpanel.EditPanelController;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

import static org.konkuk.degreeverifier.ui.Borders.EDIT_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.CRITERIA_VERIFIER;
import static org.konkuk.degreeverifier.ui.Strings.LECTURE_VERIFIER;

public class EditPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();

    public final JTextField degreeNameField = new JTextField();

    public final JRadioButton useCriteriaRadioButton = new JRadioButton(CRITERIA_VERIFIER);
    public final JTextField memoField = new JTextField();
    public final JCheckBox useMinimumPassCheckBox = new JCheckBox("최소 선택 개수:");
    public final JSpinner minimumPassSpinner = new JSpinner();
    public final JCheckBox useMaximumPassCheckBox = new JCheckBox("최대 선택 개수:");
    public final JSpinner maximumPassSpinner = new JSpinner();
    public final JCheckBox needAllPassCheckBox = new JCheckBox("필수 검사 조건");

    public final JRadioButton useLectureRadioButton = new JRadioButton(LECTURE_VERIFIER);
    public final JTextField lectureNameField = new JTextField();
    public final JCheckBox useMinimumSemesterCheckBox = new JCheckBox("유효 기간 시작:");
    public final JComboBox<Semester> minimumSemesterComboBox = new JComboBox<>();
    public final JCheckBox useMaximumSemesterCheckBox = new JCheckBox("유효 기간 종료:");
    public final JComboBox<Semester> maximumSemesterComboBox = new JComboBox<>();
    public final JCheckBox setNonExclusiveCheckBox = new JCheckBox("타 학위에서의 중복 사용을 허용");

    public EditPanel() {
        setLayout(new GridBagLayout());
        setMinimumSize(MINIMUM_EDIT_PANEL_SIZE);
        setBorder(EDIT_PANEL_BORDER);
        
        initGridBackConstraints();
        initDegreeEditor();
        initCriteriaEditor();
        initRecursiveEditor();
        initLectureEditor();

        ButtonGroup group = new ButtonGroup();
        group.add(useLectureRadioButton);
        group.add(useCriteriaRadioButton);

        new EditPanelController(this);
    }

    private void initGridBackConstraints() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
    }

    private void initDegreeEditor() {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new JLabel("학위 이름:"), gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        add(degreeNameField, gbc);
    }

    private void initCriteriaEditor() {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(needAllPassCheckBox, gbc);
        gbc.gridwidth = 1;
    }

    private void initRecursiveEditor() {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(useCriteriaRadioButton, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(useMinimumPassCheckBox, 1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(minimumPassSpinner, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(useMaximumPassCheckBox, 1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(maximumPassSpinner, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(new JLabel("메모:"), 2), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(memoField, gbc);
    }

    private void initLectureEditor() {
        gbc.gridx = 0;
        gbc.gridy++;
        add(useLectureRadioButton, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(new JLabel("과목명:"), 2), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(lectureNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(useMinimumSemesterCheckBox, 1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(minimumSemesterComboBox, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        add(new IndentedGridCell(useMaximumSemesterCheckBox, 1), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(maximumSemesterComboBox, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(new IndentedGridCell(setNonExclusiveCheckBox, 1), gbc);
    }
}
