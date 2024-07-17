package org.konkuk.degreeverifier.business.verify.snapshot;

import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;

import java.util.*;
import java.util.stream.Collectors;

public class DegreeSnapshot implements Snapshot {
    public final DegreeCriteria criteria;
    public final boolean verified;
    public final int totalCredit;
    public final RecursiveSnapshot recursiveSnapshot;
    public final Set<String> sufficientDegrees = new LinkedHashSet<>();
    public final Set<String> insufficientDegrees = new LinkedHashSet<>();
    public final LinkedList<LectureSnapshot> lectureSnapshots = new LinkedList<>();

    public DegreeSnapshot(
            DegreeCriteria criteria,
            boolean verified,
            int totalCredit,
            RecursiveSnapshot recursiveSnapshot
    ) {
        this.criteria = new DegreeCriteria(criteria);
        this.verified = verified;
        this.totalCredit = totalCredit;
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

    public DegreeSnapshot(
            String degreeName,
            int version,
            int totalCredit,
            String[] lectureNames,
            Integer[] lectureCredits
    ) {
        this.criteria = new DegreeCriteria(
                degreeName,
                version,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        this.verified = true;
        this.totalCredit = totalCredit;
        this.recursiveSnapshot = null;
        for (int i = 0; i < lectureNames.length; i++) {
            if(lectureNames[i] == null) {
                break;
            }
            lectureSnapshots.add(new LectureSnapshot(lectureNames[i], lectureCredits[i]));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DegreeSnapshot)) {
            return false;
        }
        DegreeSnapshot o = (DegreeSnapshot) obj;

        if (!Objects.equals(criteria.degreeName, o.criteria.degreeName)) {
            return false;
        }
        if(!Objects.equals(criteria.version, o.criteria.version)) {
            return false;
        }

        Set<LectureSnapshot> l1 = lectureSnapshots.stream().filter(l -> l.verified).collect(Collectors.toSet());
        Set<LectureSnapshot> l2 = o.lectureSnapshots.stream().filter(l -> l.verified).collect(Collectors.toSet());

        return Objects.equals(l1, l2);
    }

    @Override
    public String toString() {
        return criteria.toString();
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append(criteria.version).append(",")
                .append(criteria.degreeName).append(",")
                .append(totalCredit).append(",");

        for (LectureSnapshot lectureSnapshot : lectureSnapshots) {
            if (lectureSnapshot.matched != null) {
                sb.append(lectureSnapshot.matched.name).append(",")
                        .append(lectureSnapshot.matched.credit).append(",");
            }
        }

        return sb.toString();
    }
}
