package org.konkuk.degreeverifier.logic.informationtree;

import org.konkuk.degreeverifier.business.AppModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.components.informationtree.InformationTree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

public class InformationTreeModel extends DefaultTreeModel {
    private final AppModel appModel = AppModel.getInstance();

    private final DefaultMutableTreeNode root;
    private final InformationTree tree;

    public InformationTreeModel(InformationTree tree) {
        super(new DefaultMutableTreeNode("/"));
        this.tree = tree;
        root = (DefaultMutableTreeNode) getRoot();

        appModel.observe(AppModel.ObserveOn.ON_INFORMATION_TARGET_UPDATED, this::_updateTree);
    }

    private void _updateTree(Object o) {
        List<DegreeSnapshot> selectedDegrees = (List<DegreeSnapshot>) o;
        updateTree(selectedDegrees);
    }

    private void updateTree(List<DegreeSnapshot> selectedDegrees) {
        root.removeAllChildren();

        for (DegreeSnapshot selectedDegree : selectedDegrees) {
            if (selectedDegree == null) {
                continue;
            }
            DefaultMutableTreeNode degreeNode = new DefaultMutableTreeNode(selectedDegree);
            DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode(
                    "인정 기준: " + selectedDegree.criteria.minimumCredit + " 학점 이상 교과목 이수"
            );
            degreeNode.add(minimumCreditNode);

            addNode(degreeNode, selectedDegree.recursiveSnapshot);
            root.add(degreeNode);
        }
        reload();
        tree.updateFoldingState();
    }

    private void addNode(DefaultMutableTreeNode parent, RecursiveSnapshot snapshot) {
        DefaultMutableTreeNode recursiveNode = new DefaultMutableTreeNode(snapshot);

        if (snapshot.lectureSnapshot != null) {
            addNode(recursiveNode, snapshot.lectureSnapshot);
        } else {
            if (snapshot.criteria.needsAllPass()) {
                recursiveNode.add(new DefaultMutableTreeNode("통과 기준: 모든 하위 검사 통과"));
            } else if (snapshot.criteria.minimumPass != null) {
                recursiveNode.add(new DefaultMutableTreeNode(
                        "통과 기준: 최소 " + snapshot.criteria.minimumPass + "개의 하위 검사 통과"));
            }

            if (snapshot.criteria.maximumPass != null) {
                recursiveNode.add(new DefaultMutableTreeNode(
                        "학점 제한: 최대 " + snapshot.criteria.maximumPass + "개의 교과목"));
            }

            for (RecursiveSnapshot subSnapshot : snapshot.subSnapshots) {
                addNode(recursiveNode, subSnapshot);
            }
        }

        parent.add(recursiveNode);
    }

    private void addNode(DefaultMutableTreeNode parent, LectureSnapshot snapshot) {
        parent.add(new DefaultMutableTreeNode("과목명: " + snapshot.criteria.lectureName));
        if (snapshot.criteria.minimumGrade != null) {
            parent.add(new DefaultMutableTreeNode("인정 성적: " + snapshot.criteria.minimumGrade + " 이상"));
        }
        String validPeriodPrefix = "유효 기간:";
        String validPeriodPostfix = " 교과목만 인정";
        String validPeriod = "";
        if (snapshot.criteria.minimumYear != null) {
            validPeriod += " " + snapshot.criteria.minimumYear + "년도";
            if (snapshot.criteria.minimumSemester != null) {
                validPeriod += " " + snapshot.criteria.minimumSemester + "학기";
            }
        }
        if (!validPeriod.isEmpty()) {
            validPeriod += " ~";
        }
        if (snapshot.criteria.maximumYear != null) {
            validPeriod += " " + snapshot.criteria.maximumYear + "년도";
        }
        if (snapshot.criteria.maximumSemester != null) {
            validPeriod += " " + snapshot.criteria.maximumSemester + "학기";
        }
        if (!validPeriod.isEmpty()) {
            parent.add(new DefaultMutableTreeNode(validPeriodPrefix + validPeriod + validPeriodPostfix));
        }
        parent.add(new DefaultMutableTreeNode("타 학위 중복: 인정 " + (snapshot.criteria.isNonExclusive() ? "가능" : "불가")));
    }
}
