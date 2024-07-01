package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.common.components.LabeledSeparator;
import org.konkuk.degreeverifier.editorframe.logic.editpanel.EditPanelListener;
import org.konkuk.degreeverifier.editorframe.logic.editpanel.EditPanelPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

import static org.konkuk.degreeverifier.ui.Borders.EDIT_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;
import static org.konkuk.degreeverifier.ui.Strings.CRITERIA_VERIFIER;
import static org.konkuk.degreeverifier.ui.Strings.LECTURE_VERIFIER;

public class EditInnerPannel extends JPanel {
    private static final int COLUMNS = 2;

    private final GridBagConstraints gbc = new GridBagConstraints();

    public final JLabel degreeNameLabel = new JLabel("학위 이름:");
    public final JLabel degreeVersionLabel = new JLabel("학위 버전(개정 차수):");
    public final JSpinner degreeVersionSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
    public final JTextField degreeNameField = new JTextField();
    public final JLabel degreeMinimumCreditLabel = new JLabel("필요 학점:");
    public final JSpinner degreeMinimumCreditSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
    public final JCheckBox useDegreeMinimumSemesterCheckBox = new JCheckBox("유효 기간 시작:");
    public final JComboBox<Semester> degreeMinimumSemesterComboBox = new JComboBox<>();
    public final JCheckBox useDegreeMaximumSemesterCheckBox = new JCheckBox("유효 기간 종료:");
    public final JComboBox<Semester> degreeMaximumSemesterComboBox = new JComboBox<>();
    public final JLabel degreeDescriptionLabel = new JLabel("메모:");
    public final JTextField degreeDescriptionField = new JTextField();

    public final JCheckBox setImportantCheckBox = new JCheckBox("반드시 통과해야 하는 검사로 설정");
    public final JLabel criteriaDescriptionLabel = new JLabel("메모:");
    public final JTextField criteriaDescriptionField = new JTextField();

    public final JRadioButton useRecursiveRadioButton = new JRadioButton(CRITERIA_VERIFIER);
    public final JCheckBox setNeedAllPassCheckBox = new JCheckBox("모든 하위 검사를 통과해야 함");
    public final JCheckBox useMinimumPassCheckBox = new JCheckBox("통과 수 하한:");
    public final JSpinner minimumPassSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
    public final JCheckBox useMaximumPassCheckBox = new JCheckBox("통과 수 상한:");
    public final JSpinner maximumPassSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));

    public final JRadioButton useLectureRadioButton = new JRadioButton(LECTURE_VERIFIER);
    public final JLabel lectureNameLabel = new JLabel("과목명:");
    public final JTextField lectureNameField = new JTextField();
    public final JCheckBox useMinimumSemesterCheckBox = new JCheckBox("유효 기간 시작:");
    public final JComboBox<Semester> minimumSemesterComboBox = new JComboBox<>();
    public final JCheckBox useMaximumSemesterCheckBox = new JCheckBox("유효 기간 종료:");
    public final JComboBox<Semester> maximumSemesterComboBox = new JComboBox<>();
    public final JCheckBox setNonExclusiveCheckBox = new JCheckBox("타 학위에서의 중복 사용을 허용");
    public final JLabel lectureDescriptionLabel = new JLabel("메모:");
    public final JTextField lectureDescriptionField = new JTextField();

    public EditInnerPannel() {
        setLayout(new GridBagLayout());
        setMinimumSize(MINIMUM_EDIT_PANEL_SIZE);
        setBorder(EDIT_PANEL_BORDER);

        initGridBackConstraints();
        initDegreeEditor();
        addSeparator("검사 설정");
        initCriteriaEditor();
        addSeparator("하위 검사 설정");
        initRecursiveEditor();
        initLectureEditor();

        ButtonGroup g1 = new ButtonGroup();
        g1.add(useLectureRadioButton);
        g1.add(useRecursiveRadioButton);
        useRecursiveRadioButton.setSelected(true);

        Semester s = new Semester(2022, Semester.Type.FIRST);
        while (s.year <= Calendar.getInstance().get(Calendar.YEAR) + 4) {
            degreeMinimumSemesterComboBox.addItem(s);
            degreeMaximumSemesterComboBox.addItem(s);
            minimumSemesterComboBox.addItem(s);
            maximumSemesterComboBox.addItem(s);
            s = s.next();
        }

        new EditPanelPresenter(this);
        new EditPanelListener(this);
    }

    private void initDegreeEditor() {
        addRow(degreeNameLabel, degreeNameField);
        addRow(degreeVersionLabel, degreeVersionSpinner);
        addRow(degreeMinimumCreditLabel, degreeMinimumCreditSpinner);
        addRow(useDegreeMinimumSemesterCheckBox, degreeMinimumSemesterComboBox);
        addRow(useDegreeMaximumSemesterCheckBox, degreeMaximumSemesterComboBox);
        addRow(degreeDescriptionLabel, degreeDescriptionField);
    }

    private void initCriteriaEditor() {
        addRow(setImportantCheckBox);
        addRow(criteriaDescriptionLabel, criteriaDescriptionField, 1);
    }

    private void initRecursiveEditor() {
        addRow(useRecursiveRadioButton);
        addRow(setNeedAllPassCheckBox, 1);
        addRow(useMinimumPassCheckBox, minimumPassSpinner, 1);
        addRow(useMaximumPassCheckBox, maximumPassSpinner, 1);
    }

    private void initLectureEditor() {
        addRow(useLectureRadioButton);
        addRow(lectureNameLabel, lectureNameField, 2);
        addRow(useMinimumSemesterCheckBox, minimumSemesterComboBox, 1);
        addRow(useMaximumSemesterCheckBox, maximumSemesterComboBox, 1);
        addRow(setNonExclusiveCheckBox, 1);
        addRow(lectureDescriptionLabel, lectureDescriptionField, 2);
    }

    private void addSeparator(String label) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = COLUMNS;
        gbc.insets = new Insets(12, 0, 12, 0);
        add(new LabeledSeparator(label), gbc);
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.gridwidth = 1;
    }

    private void addRow(JComponent col1) {
        addRow(col1, 0);
    }

    private void addRow(JComponent col1, int indent) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = COLUMNS;
        gbc.insets.left = indent * 22;
        add(col1, gbc);
        gbc.gridwidth = 1;
        gbc.insets.left = 0;
    }

    private void addRow(JComponent col1, JComponent col2) {
        addRow(col1, col2, 0);
    }

    private void addRow(JComponent col1, JComponent col2, int indent) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.2;
        gbc.insets.left = indent * 22;
        add(col1, gbc);
        gbc.insets.left = 0;

        gbc.gridx++;
        gbc.weightx = 1;
        if (!(col2 instanceof JTextField)) {
            gbc.fill = GridBagConstraints.NONE;
        }
        add(col2, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    private void initGridBackConstraints() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);
    }
}
