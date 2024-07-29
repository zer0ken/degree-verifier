package org.konkuk.degreeverifier.commitframe.logic.tables;

import org.konkuk.degreeverifier.business.csv.Transcript;
import org.konkuk.degreeverifier.business.models.AppModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TranscriptTableModel extends DefaultTableModel {
    AppModel appModel = AppModel.getInstance();

    public TranscriptTableModel() {
        setColumnIdentifiers(new Vector<>(Transcript.ColumnName.getNames()));
        if (appModel.isTranscriptLoaded()) {
            setColumnIdentifiers(new Vector<>(appModel.getTranscriptTableHeader()));
            update();
        }
        appModel.observe(AppModel.On.TRANSCRIPT_LOADED, unused ->{
            setColumnIdentifiers(new Vector<>(appModel.getTranscriptTableHeader()));
            update();
        });
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
