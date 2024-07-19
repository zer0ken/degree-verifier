package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.csv.Commit;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class CommitTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public CommitTableModel() {
        setColumnIdentifiers(new Vector<>(Commit.ColumnName.getNames()));
        if (appModel.isTranscriptLoaded()) {
            update();
            setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
        }
        appModel.observe(AppModel.On.COMMIT_UPDATED, unused -> {
            update();
            setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
        });
        appModel.observe(AppModel.On.COMMIT_LOADED, unused -> {
            update();
            setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
        });
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, unused -> {
            if (appModel.isCommitLoaded()) {
                update();
                setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
            }
        });
    }

    private void update() {
        setRowCount(0);
        for (List<String> row : appModel.getCommitTable()) {
            addRow(row.toArray());
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
