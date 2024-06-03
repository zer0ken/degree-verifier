package org.konkuk.degreeverifier.business;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.logic.verifiedlist.items.VerifiedDegreeListItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class InformationModel extends Observable {
    protected static final InformationModel instance = new InformationModel();

    protected InformationModel() {
    }

    public static InformationModel getInstance() {
        return instance;
    }

    private final List<DegreeSnapshot> informationTargets = Collections.synchronizedList(new LinkedList<>());

    public void updateInformationTarget(List<?> targets) {
        if (targets.isEmpty()) {
            return;
        }
        informationTargets.clear();

        if (targets.get(0) instanceof DegreeSnapshot) {
            for (Object target : targets) {
                informationTargets.add(((DegreeSnapshot) target));
            }
        } else if (targets.get(0) instanceof VerifiedDegreeListItem) {
            for (Object target : targets) {
                VerifiedDegreeListItem item = (VerifiedDegreeListItem) target;
                if(item.getDegreeSnapshot() != null) {
                    informationTargets.add(item.getDegreeSnapshot());
                }
            }
        }

        notify(On.INFORMATION_TARGET_UPDATED, informationTargets);
    }

    public List<DegreeSnapshot> getInformationTargets() {
        return informationTargets;
    }

    public enum On implements Event {
        INFORMATION_TARGET_UPDATED
    }
}
