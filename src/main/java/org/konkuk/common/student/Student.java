package org.konkuk.common.student;

import com.sun.media.sound.InvalidFormatException;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.FileUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Student extends LinkedList<Lecture> {
    private final String directoryName;
    public final String id;
    public final String name;

    private boolean isLoaded = false;

    public Student(String directoryName) throws InvalidFormatException {
        File directory = new File(directoryName);
        StringTokenizer tokenizer = new StringTokenizer(directory.getName(), "-");
        if (tokenizer.countTokens() != 2) {
            throw new InvalidFormatException("Wrong directory name format: " + directoryName);
        }
        this.directoryName = directoryName;
        this.id = tokenizer.nextToken().trim();
        this.name = tokenizer.nextToken().trim();
    }
    
    private void loadLectures(ProgressTracker tracker) {
        File directory = new File(directoryName);
        File[] transcripts = directory.listFiles();
        if (transcripts == null) {
            tracker.finish();
            return;
        }
        tracker.setMaximum(transcripts.length);
        for (File transcript : transcripts) {
            if (transcript.isFile() && transcript.getName().endsWith(".tsv")) {
                List<String[]> tokenizedLines = FileUtil.fromTsvFile(transcript.getAbsolutePath());
                tokenizedLines.forEach(tokens -> add(
                        new Lecture(
                                tokens[0], tokens[1], tokens[2], tokens[3], tokens[4],
                                Integer.parseInt(tokens[5]),
                                tokens[6], tokens[7]
                        )
                ));
            }
            tracker.increment();
        }
        isLoaded = true;
        tracker.finish();
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public String toString() {
        return directoryName;
    }
}
