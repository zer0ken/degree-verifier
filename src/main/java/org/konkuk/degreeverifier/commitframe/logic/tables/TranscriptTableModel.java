package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.models.AppModel;
import org.konkuk.degreeverifier.business.verify.csv.Transcript;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TranscriptTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public TranscriptTableModel() {
        setColumnIdentifiers(Transcript.HEADER);
        if (appModel.isTranscriptLoaded()) {
            update();
        }
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, unused -> update());
    }

    private void update() {
        setRowCount(0);
        for (List<String> row : appModel.getTranscriptTable()) {
            addRow(row.toArray());
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
