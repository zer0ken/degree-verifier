package org.konkuk.common;

import org.konkuk.common.criteria.DegreeCriteria;
import org.konkuk.common.snapshot.DegreeSnapshot;
import org.konkuk.common.verifier.DegreeVerifier;
import org.konkuk.common.verifier.LectureVerifier;
import org.paukov.combinatorics3.Generator;

import java.io.File;
import java.util.*;

/**
 * 수강 내역을 참조하여 이수 가능한 학위를 검사하는 클래스입니다.
 * 입력받은 경로로부터 학위 검사 기준과 수강 내역을 불러와 사용합니다.
 *
 * @author 이현령
 * @since 2024-05-24T22:53:22.711Z
 */
public class DegreeManager {
    private final List<DegreeVerifier> degreeVerifiers;
    private final List<Lecture> lectures;
    private final Map<String, List<DegreeSnapshot>> verifiedDegreeMap;

    public DegreeManager() {
        degreeVerifiers = new ArrayList<>();
        lectures = new ArrayList<>();
        verifiedDegreeMap = new LinkedHashMap<>();
    }

    public void loadAllVerifier(String directoryName) {
        File directory = new File(directoryName);
        File[] specs = directory.listFiles();
        if (specs == null) {
            return;
        }
        for (File spec : specs) {
            if (spec.isFile() && spec.getName().endsWith(".json")) {
                loadVerifier(spec.getAbsolutePath());
            }
        }
    }

    public void loadVerifier(String filename) {
        degreeVerifiers.add(new DegreeVerifier(FileUtil.fromJsonFile(filename, DegreeCriteria.class)));
    }

    public void loadLectures(String filename) {
        List<String[]> tokenizedLines = FileUtil.fromTsvFile(filename);
        tokenizedLines.forEach(tokens -> lectures.add(
                new Lecture(
                        tokens[0], tokens[1], tokens[2], tokens[3], tokens[4],
                        Integer.parseInt(tokens[5]),
                        tokens[6], tokens[7]
                )
        ));
    }

    public void verify() {
        if (degreeVerifiers.isEmpty()) {
            throw new RuntimeException("No verifier loaded");
        }
        if (lectures.isEmpty()) {
            throw new RuntimeException("No lectures loaded");
        }

        verifiedDegreeMap.clear();

        List<LectureVerifier> matchedLectureVerifiers = new ArrayList<>();
        degreeVerifiers.forEach(verifier -> matchedLectureVerifiers.addAll(verifier.match(lectures)));

        if (degreeVerifiers.stream().allMatch(DegreeVerifier::isPruned)) {
            return;
        }

        for (int releaseCount = 0; releaseCount < matchedLectureVerifiers.size(); releaseCount++) {
            Generator.combination(matchedLectureVerifiers).simple(releaseCount).stream().forEach(toRelease -> {
                matchedLectureVerifiers.forEach(LectureVerifier::release);
                matchedLectureVerifiers.stream()
                        .filter(verifier -> !toRelease.contains(verifier))
                        .forEach(LectureVerifier::hold);

                List<DegreeSnapshot> verifiedDegrees = new LinkedList<>();
                StringBuilder sb = new StringBuilder();

                degreeVerifiers.forEach(verifier -> {
                    boolean verified = !verifier.isPruned() && verifier.verify();
                    sb.append(verified ? "T" : "F");
                    if (verified) {
                        verifiedDegrees.add((DegreeSnapshot) verifier.takeSnapshot());
                    }
                });

                if (sb.toString().contains("T") && !verifiedDegreeMap.containsKey(sb.toString())) {
                    verifiedDegreeMap.put(sb.toString(), verifiedDegrees);
                }
            });
        }
    }

    public List<DegreeVerifier> getDegreeVerifiers() {
        return degreeVerifiers;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public Map<String, List<DegreeSnapshot>> getVerifiedDegreeMap() {
        return verifiedDegreeMap;
    }
}
