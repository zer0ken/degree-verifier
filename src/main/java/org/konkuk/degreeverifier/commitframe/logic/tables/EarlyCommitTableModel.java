package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.csv.Commit;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EarlyCommitTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public EarlyCommitTableModel() {
        setColumnIdentifiers(Commit.HEADER);
        if (appModel.isCommitLoaded()) {
            update();
        }
        appModel.observe(AppModel.On.COMMIT_LOADED, unused -> update());
    }

    private void update() {
        setRowCount(0);
        for (List<String> row : appModel.getEarlyCommitTable()) {
            addRow(row.toArray());
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
