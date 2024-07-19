package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.csv.Commit;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CommitTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public CommitTableModel() {
        setColumnIdentifiers(Commit.HEADER);
        if (appModel.isTranscriptLoaded()) {
            update();
        }
        appModel.observe(AppModel.On.COMMIT_UPDATED, unused -> update());
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
