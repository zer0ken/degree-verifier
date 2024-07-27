package org.konkuk.degreeverifier.business.csv;

import java.util.ArrayList;
import java.util.List;

public class CommitRow extends ArrayList<String> implements CsvExportable {
    public CommitRow(List<String> row) {
        super(row);
    }

    @Override
    public String set(int index, String element) {
        while (index >= size()) {
            add("");
        }
        return super.set(index, element);
    }

    @Override
    public String toCsv() {
        return String.join(",", this);
    }
}
