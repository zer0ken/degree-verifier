package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.common.components.IndentedGridCell;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

import static org.konkuk.degreeverifier.ui.Borders.EDIT_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.CRITERIA_VERIFIER;
import static org.konkuk.degreeverifier.ui.Strings.LECTURE_VERIFIER;

public class EditPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();

    private final JTextField degreeNameField = new JTextField();

    private final JRadioButton useCriteriaRadioButton = new JRadioButton(CRITERIA_VERIFIER);
    private final JTextField memoField = new JTextField();
    private final JCheckBox useMinimumPassCheckBox = new JCheckBox("최소 선택 개수:");
    private final JSpinner minimumPassSpinner = new JSpinner();
    private final JCheckBox useMaximumPassCheckBox = new JCheckBox("최대 선택 개수:");
    private final JSpinner maximumPassSpinner = new JSpinner();
    private final JCheckBox needAllPassCheckBox = new JCheckBox("필수 검사 조건");

    private final JRadioButton useLectureRadioButton = new JRadioButton(LECTURE_VERIFIER);
    private final JTextField lectureNameField = new JTextField();
    private final JCheckBox useMinimumSemesterCheckBox = new JCheckBox("유효 기간 시작:");
    private final JComboBox<Semester> minimumSemesterComboBox = new JComboBox<>();
    private final JCheckBox useMaximumSemesterCheckBox = new JCheckBox("유효 기간 종료:");
    private final JComboBox<Semester> maximumSemesterComboBox = new JComboBox<>();
    private final JCheckBox setNonExclusiveCheckBox = new JCheckBox("타 학위에서의 중복 사용을 허용");

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
        Semester s = new Semester(2022, Semester.Type.FIRST);
        while (s.year <= Calendar.getInstance().get(Calendar.YEAR) + 4) {
            minimumSemesterComboBox.addItem(s);
            maximumSemesterComboBox.addItem(s);
            s = s.next();
        }
        useLectureRadioButton.addChangeListener(
                e -> {
                    lectureNameField.setEnabled(useLectureRadioButton.isSelected());
                    useMinimumSemesterCheckBox.setEnabled(useLectureRadioButton.isSelected());
                    useMaximumSemesterCheckBox.setEnabled(useLectureRadioButton.isSelected());
                    setNonExclusiveCheckBox.setEnabled(useLectureRadioButton.isSelected());
                }
        );
        useMinimumSemesterCheckBox.addChangeListener(
                e -> minimumSemesterComboBox.setEnabled(useMinimumSemesterCheckBox.isSelected())
        );
        useMaximumSemesterCheckBox.addChangeListener(
                e -> maximumSemesterComboBox.setEnabled(useMaximumSemesterCheckBox.isSelected())
        );

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
