package org.konkuk.common;

import org.konkuk.common.verifier.DegreeVerifier;
import org.konkuk.common.verifier.LectureVerifier;
import org.paukov.combinatorics3.Generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 수강 내역을 참조하여 이수 가능한 학위를 검사하는 클래스입니다.
 * 입력받은 위치로부터 파일을 읽어 학위 검사 조건과 수강 내역을 불러와 사용합니다.
 *
 * @author 이현령
 * @since 2024-05-24T22:53:22.711Z
 */
public class DegreeManager {
    private final List<DegreeVerifier> degreeVerifiers;
    private final List<Lecture> lectures;

    public DegreeManager() {
        degreeVerifiers = new ArrayList<>();
        lectures = new ArrayList<>();
    }

    public void loadAllVerifier(String directoryName) {
        File directory = new File(directoryName);
        File[] specs = directory.listFiles();
        if (specs == null) {
            return;
        }
        for (File spec : specs) {
            if (spec.isFile()) {
                loadVerifier(spec.getAbsolutePath());
            }
        }
    }

    public void loadVerifier(String filename) {
        degreeVerifiers.add(FileUtil.fromJsonFile(filename, DegreeVerifier.class));
    }

    public void loadLectures(String filename) {
        List<String[]> tokenizedLines = FileUtil.fromTsvFile(filename);
        tokenizedLines.forEach(tokens -> lectures.add(
                new Lecture(
                        Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]),
                        tokens[2], tokens[3], tokens[4],
                        Integer.parseInt(tokens[5]),
                        tokens[6]
                )
        ));
    }

    public List<DegreeVerifier> verify() {
        if (degreeVerifiers.isEmpty()) {
            throw new RuntimeException("No verifier loaded");
        }
        if (lectures.isEmpty()) {
            throw new RuntimeException("No lectures loaded");
        }

        List<LectureVerifier> matchedLectureVerifiers = new ArrayList<>();
        degreeVerifiers.forEach(verifier -> matchedLectureVerifiers.addAll(verifier.match(lectures)));

        for (int releaseCount = 0; releaseCount < matchedLectureVerifiers.size(); releaseCount++) {
            Generator.combination(matchedLectureVerifiers).simple(releaseCount).stream().forEach(toRelease -> {
                matchedLectureVerifiers.forEach(LectureVerifier::release);
                matchedLectureVerifiers.stream().filter(verifier -> !toRelease.contains(verifier))
                        .forEach(LectureVerifier::hold);
                degreeVerifiers.forEach(DegreeVerifier::verify);
            });
        }

        return null;
    }
}
