package org.konkuk.degreeverifier.business.verify.snapshot;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class DegreeSnapshot implements Snapshot, Creditizable {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final RecursiveSnapshot recursiveSnapshot;
    public final Set<String> sufficientDegrees = new LinkedHashSet<>();
    public final Set<String> insufficientDegrees = new LinkedHashSet<>();
    public final LinkedList<LectureSnapshot> lectureSnapshots = new LinkedList<>();

    public DegreeSnapshot(DegreeCriteria criteria, boolean verified, RecursiveSnapshot recursiveSnapshot) {
        this.criteria = new DegreeCriteria(criteria);
        this.verified = verified;
        this.recursiveSnapshot = recursiveSnapshot;

        LinkedList<RecursiveSnapshot> queue = new LinkedList<>();
        queue.add(recursiveSnapshot);
        while (!queue.isEmpty()) {
            RecursiveSnapshot cur = queue.pop();
            if (cur.lectureSnapshot != null) {
                lectureSnapshots.add(cur.lectureSnapshot);
            } else {
                queue.addAll(Arrays.asList(cur.subSnapshots));
            }
        }
    }

    @Override
    public int creditize() {
        return 0;
    }

    @Override
    public String toString() {
        return criteria.toString();
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(criteria.version).append(",")
                .append(criteria.degreeName).append(",");

        for (LectureSnapshot lectureSnapshot : lectureSnapshots) {
            if (lectureSnapshot.matched != null) {
                sb.append(lectureSnapshot.matched.name).append(",")
                        .append(lectureSnapshot.matched.credit).append(",");
            }
        }

        return sb.toString();
    }
}
