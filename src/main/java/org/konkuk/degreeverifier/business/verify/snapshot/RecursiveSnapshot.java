package org.konkuk.degreeverifier.business.verify.snapshot;

import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.verifier.Creditizable;

import java.util.Arrays;

public class RecursiveSnapshot implements Snapshot, Creditizable {
    public final RecursiveCriteria criteria;
    public final boolean verified;

    public final LectureSnapshot lectureSnapshot;
    public final RecursiveSnapshot[] subSnapshots;

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, RecursiveSnapshot[] subSnapshots) {
        this.criteria = new RecursiveCriteria(criteria);
        this.verified = verified;
        this.lectureSnapshot = null;
        this.subSnapshots = subSnapshots;
    }

    public RecursiveSnapshot(RecursiveCriteria criteria, boolean verified, LectureSnapshot lectureSnapshot) {
        this.criteria = new RecursiveCriteria(criteria);
        this.verified = verified;
        this.lectureSnapshot = lectureSnapshot;
        this.subSnapshots = null;
    }

    @Override
    public int creditize() {
        if (!verified) {
            return 0;
        }
        if (lectureSnapshot != null) {
            return lectureSnapshot.creditize();
        } else if (criteria.maximumPass != null) {
            return Arrays.stream(subSnapshots)
                    .sorted((a, b) -> b.creditize() - a.creditize())
                    .limit(criteria.maximumPass)
                    .reduce(0, (acc, snapshot) -> acc + snapshot.creditize(), Integer::sum);
        } else {
            return Arrays.stream(subSnapshots).reduce(0, (acc, snapshot) -> acc + snapshot.creditize(), Integer::sum);
        }
    }

    @Override
    public String toString() {
        String str = criteria.description != null ? criteria.description + " - " : "";
        if (criteria.isImportant()) {
            str += "필수 ";
        }
        if (lectureSnapshot != null) {
            str += "교과목: " + lectureSnapshot;
        } else {
            str += "검사 그룹(" + subSnapshots.length + ")";
        }
        if (verified) {
            str += " - 총 " + creditize() + " 학점";
        }
        return str;
    }
}
