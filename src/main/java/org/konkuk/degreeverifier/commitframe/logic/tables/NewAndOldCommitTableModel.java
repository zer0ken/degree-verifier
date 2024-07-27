package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.csv.Commit;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class NewAndOldCommitTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public NewAndOldCommitTableModel() {
        setColumnIdentifiers(new Vector<>(Commit.ColumnName.getNames()));
        appModel.observe(AppModel.On.COMMIT_UPDATED, unused -> {
            update();
        });
    }

    private void update() {
        setRowCount(0);
        for (List<String> row : appModel.getCommitTableNewAndOld()) {
            addRow(row.toArray());
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
