package org.konkuk.common.verify;

import org.konkuk.common.verify.snapshot.DegreeSnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * 학위 이름과 학위에 대한 검사 결과 스냅샷을 각각 키와 값으로 가지는 맵 자료구조입니다.
 *
 * @author 이현령
 * @since 2024-05-31T21:30:03.075Z
 */
public class SnapshotBundle extends TreeMap<String, DegreeSnapshot> {
    public boolean hasSameKeys(SnapshotBundle other) {
        if (size() != other.size()){
            return false;
        }

        List<String> l = new LinkedList<>(keySet());
        List<String> r = new LinkedList<>(other.keySet());

        for (int i = 0; i < size(); i++) {
            if (!l.equals(r)) {
                return false;
            }
        }

        return true;
    }
}
