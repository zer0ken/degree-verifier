package org.konkuk.degreeverifier.business.models;

import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.common.logic.VerifierListItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class InformationModel extends Observable {
    protected static final InformationModel instance = new InformationModel();

    protected InformationModel() {
    }

    public static InformationModel getInstance() {
        return instance;
    }

    private final ExecutorService executorService = MyExecutorService.getInstance();
    private final List<DegreeSnapshot> informationTargets = Collections.synchronizedList(new LinkedList<>());

    public void updateInformationTarget(List<?> targets) {
        executorService.submit(() -> {
            informationTargets.clear();
            if (targets.isEmpty()) {
                return;
            }
            for (Object target : targets) {
                VerifierListItem item = (VerifierListItem) target;
                if (item.getSnapshot() != null) {
                    informationTargets.add(item.getSnapshot());
                }
            }

            notify(On.INFORMATION_TARGET_UPDATED, informationTargets);
        });
    }

    public enum On implements Event {
        INFORMATION_TARGET_UPDATED
    }
}
