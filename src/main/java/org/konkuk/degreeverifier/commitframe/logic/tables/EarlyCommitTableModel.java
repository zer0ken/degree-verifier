package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.csv.Commit;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class EarlyCommitTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public EarlyCommitTableModel() {
        setColumnIdentifiers(new Vector<>(Commit.ColumnName.getNames()));
        if (appModel.isCommitLoaded()) {
            setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
            update();
        }
        appModel.observe(AppModel.On.COMMIT_LOADED, unused -> {
            setColumnIdentifiers(new Vector<>(appModel.getEarlyCommitTableHeader()));
            update();
        });
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
