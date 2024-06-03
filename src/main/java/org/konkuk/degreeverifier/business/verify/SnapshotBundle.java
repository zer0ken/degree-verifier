package org.konkuk.degreeverifier.business.verify;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;

import java.util.TreeMap;

/**
 * 학위 이름과 학위에 대한 검사 결과 스냅샷을 각각 키와 값으로 가지는 맵 자료구조입니다.
 *
 * @author 이현령
 * @since 2024-05-31T21:30:03.075Z
 */
public class SnapshotBundle extends TreeMap<String, DegreeSnapshot> {
}
