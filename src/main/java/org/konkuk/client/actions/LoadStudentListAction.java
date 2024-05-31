package org.konkuk.client.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.konkuk.client.AppModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.konkuk.client.ui.Strings.LOAD_STUDENT_LIST;

public class LoadStudentListAction extends AbstractAction {
    private final AppModel appModel = AppModel.getInstance();

    public LoadStudentListAction() {
        putValue(NAME, LOAD_STUDENT_LIST);
        putValue(SHORT_DESCRIPTION, LOAD_STUDENT_LIST);
        putValue(SMALL_ICON, null);
        putValue(LARGE_ICON_KEY, new FlatSVGIcon("org/konkuk/icons/sync_icon.svg", getClass().getClassLoader()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        perform();
    }

    public void perform() {
        appModel.loadStudentList();
    }
}
