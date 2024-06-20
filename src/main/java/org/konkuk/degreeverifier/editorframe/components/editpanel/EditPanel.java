package org.konkuk.degreeverifier.editorframe.components.editpanel;

import org.konkuk.degreeverifier.common.components.Description;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

import static org.konkuk.degreeverifier.ui.Borders.EDIT_PANEL_BORDER;
import static org.konkuk.degreeverifier.ui.Dimensions.EDIT_PANEL_GRID_INSETS;
import static org.konkuk.degreeverifier.ui.Dimensions.MINIMUM_EDIT_PANEL_SIZE;

public class EditPanel extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private int currentRow = 0;
    private final int MAX_COLUMNS = 4;

    public EditPanel() {
        setMinimumSize(MINIMUM_EDIT_PANEL_SIZE);
        setBorder(EDIT_PANEL_BORDER);
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = EDIT_PANEL_GRID_INSETS;

        startNewRow();
        addInRow(new JLabel("학위명:"));
        gbc.gridwidth = MAX_COLUMNS - 1;
        addInRow(new JTextField());

        startNewRow();
        addDescription("이 검사 기준에서 검사할 학위의 이름입니다.");

        startNewRow();
        addInRow(new JLabel("필요 학점:"));
        addInRow(new JSpinner());

        startNewRow();
        addVerticalSpace();

        startNewRow();
        addInRow(new JRadioButton("교과목 검사"));

        startNewRow(1);
        addDescription("조건과 일치하는 교과목을 찾는 검사 기준입니다.");

        startNewRow(1);
        addInRow(new JLabel("과목명:"));
        gbc.gridwidth = MAX_COLUMNS - 1;
        addInRow(new JTextField());

        startNewRow(1);
        gbc.gridwidth = 2;
        addInRow(new JCheckBox("중복 사용 허용"));

        startNewRow(1);
        addInRow(new JCheckBox("필요 성적:"));
        addInRow(new JComboBox<>(new Vector<>(Arrays.asList(
                "F 이상",
                "D- 이상", "D 이상", "D+ 이상",
                "C- 이상", "C 이상", "C+ 이상",
                "B- 이상", "B 이상", "B+ 이상",
                "A- 이상", "A 이상", "A+ 이상"
        ))));

        startNewRow(1);
        addInRow(new JCheckBox("유효 기간 시작"));
        startNewRow(2);
        addDescription("설정한 기간 이전에 수강한 교과목은 일치시키지 않습니다.");

        startNewRow(2);
        addInRow(new JSpinner());
        addInRow(new JLabel("년도"));
        addInRow(new JComboBox<>(new Vector<>(Arrays.asList(
                "1학기",
                "2학기"
        ))));
        addInRow(new JLabel("부터"));

        startNewRow(1);
        addInRow(new JCheckBox("유효 기간 종료"));
        startNewRow(2);
        addDescription("설정한 기간 이후에 수강한 교과목은 일치시키지 않습니다.");

        startNewRow(2);
        addInRow(new JSpinner());
        addInRow(new JLabel("년도"));
        addInRow(new JComboBox<>(new Vector<>(Arrays.asList(
                "1학기",
                "2학기"
        ))));
        addInRow(new JLabel("까지"));

        startNewRow(1);
        addInRow(new JLabel("메모:"));
        gbc.gridwidth = MAX_COLUMNS - 1;
        addInRow(new JTextField());

        startNewRow();
        addVerticalSpace();

        startNewRow();
        addInRow(new JRadioButton("검사 그룹"));

        startNewRow(1);
        addDescription("다른 검사 기준을 포함하는 검사 그룹입니다.");

        startNewRow(1);
        addInRow(new JLabel("메모:"));
        gbc.gridwidth = MAX_COLUMNS - 1;
        addInRow(new JTextField());
    }

    private void startNewRow() {
        startNewRow(0);
    }

    private void startNewRow(int indent) {
        Insets insets = new Insets(
                EDIT_PANEL_GRID_INSETS.top,
                EDIT_PANEL_GRID_INSETS.left,
                EDIT_PANEL_GRID_INSETS.bottom,
                EDIT_PANEL_GRID_INSETS.right
        );
        insets.left += indent * 22;

        gbc.gridy = ++currentRow;
        gbc.gridx = 0;
        gbc.insets = insets;
    }

    private void addDescription(String text) {
        gbc.gridx = 0;
        gbc.gridwidth = MAX_COLUMNS;
        add(new Description(text), gbc);
        gbc.insets = EDIT_PANEL_GRID_INSETS;
        gbc.gridwidth = 1;
    }

    private void addInRow(JComponent component) {
        add(component, gbc);
        gbc.gridx++;
        gbc.insets = EDIT_PANEL_GRID_INSETS;
        gbc.gridwidth = 1;
    }

    private void addVerticalSpace() {
        add(Box.createVerticalStrut(10), gbc);
    }
}
