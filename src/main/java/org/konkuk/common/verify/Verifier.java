package org.konkuk.common.verify;

import org.konkuk.common.DefaultPaths;
import org.konkuk.common.FileUtil;
import org.konkuk.client.logic.ProgressTracker;
import org.konkuk.common.student.Lecture;
import org.konkuk.common.verify.criteria.DegreeCriteria;
import org.konkuk.common.verify.snapshot.DegreeSnapshot;
import org.konkuk.common.verify.verifier.DegreeVerifier;
import org.konkuk.common.verify.verifier.LectureVerifier;
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
public class Verifier {
    private final List<DegreeVerifier> degreeVerifiers;

    private boolean isLoaded = false;

    public Verifier() {
        degreeVerifiers = new ArrayList<>();
    }

    public Verifier(Verifier toCopy) {
        this.degreeVerifiers = new ArrayList<>();
        toCopy.degreeVerifiers.forEach(
                verifier -> degreeVerifiers.add(new DegreeVerifier(verifier))
        );
    }

    public void loadAllVerifiers(ProgressTracker tracker) {
        File directory = new File(DefaultPaths.VERIFIERS_PATH);
        File[] specs = directory.listFiles();
        if (specs == null) {
            tracker.finish();
            return;
        }
        tracker.setMaximum(specs.length);
        for (File spec : specs) {
            if (spec.isFile() && spec.getName().endsWith(".json")) {
                degreeVerifiers.add(
                        new DegreeVerifier(FileUtil.fromJsonFile(spec.getAbsolutePath(), DegreeCriteria.class))
                );
            }
            tracker.increment();
        }
        tracker.finish();
        isLoaded = true;
    }

    public void verify(List<Lecture> lectures) {
        if (degreeVerifiers.isEmpty()) {
            throw new RuntimeException("No verifier loaded");
        }
        if (lectures.isEmpty()) {
            throw new RuntimeException("No lectures loaded");
        }

        Map<String, List<DegreeSnapshot>> verifiedDegreeMap = new LinkedHashMap<>();

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

    public boolean isLoaded() {
        return isLoaded;
    }
}
