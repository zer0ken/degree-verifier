package org.konkuk.degreeverifier.mainframe.logic.studentlist;

import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.*;

public class StudentListSelectionModel extends DefaultListSelectionModel {
    private static StudentListSelectionModel instance;
    private final AppModel appModel = AppModel.getInstance();

    public StudentListSelectionModel() {
        appModel.observe(AppModel.On.COMMIT_UPDATED, (student)-> {
            resetSelection();
        });

        instance = this;
    }

    public void resetSelection() {
        if (appModel.getCommittingStudent() == null) {
            return;
        }
        clearSelection();
        int index = appModel.getCommittingStudentIndex();
        setSelectionInterval(index, index);
    }

    public static StudentListSelectionModel getInstance() {
        return instance;
    }
}
