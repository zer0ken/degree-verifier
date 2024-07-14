package org.konkuk.degreeverifier.commitframe.logic.informationtree;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.InformationModel;
import org.konkuk.degreeverifier.business.verify.snapshot.DegreeSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.commitframe.components.informationtree.InformationTree;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.nodes.InsufficientRootNode;
import org.konkuk.degreeverifier.commitframe.logic.informationtree.nodes.LecturesRootNode;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;
import java.util.stream.Collectors;

public class InformationTreeModel extends DefaultTreeModel {
    private final InformationModel informationModel = InformationModel.getInstance();

    private final DefaultMutableTreeNode root;
    private final InformationTree tree;

    public InformationTreeModel(InformationTree tree) {
        super(new DefaultMutableTreeNode("/"));
        this.tree = tree;
        root = (DefaultMutableTreeNode) getRoot();

        informationModel.observe(InformationModel.On.INFORMATION_TARGET_UPDATED, this::_updateTree);
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
                    "필요 학점: " + selectedDegree.criteria.minimumCredit + " 학점 이상"
            );
            degreeNode.add(minimumCreditNode);

            if (!selectedDegree.insufficientDegrees.isEmpty()) {
                DefaultMutableTreeNode insufficientRootNode = new InsufficientRootNode();
                for (String insufficientDegree : selectedDegree.insufficientDegrees) {
                    insufficientRootNode.add(new DefaultMutableTreeNode(insufficientDegree));
                }
                degreeNode.add(insufficientRootNode);
            }

            addNode(degreeNode, selectedDegree.recursiveSnapshot);

            List<LectureSnapshot> lectures = selectedDegree.lectureSnapshots.stream().filter(l -> l.verified).collect(Collectors.toList());
            DefaultMutableTreeNode lecturesNode = new LecturesRootNode(lectures.size());
            for (LectureSnapshot lecture : lectures) {
                lecturesNode.add(new DefaultMutableTreeNode(lecture.criteria.lectureName));
            }

            degreeNode.add(lecturesNode);

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
            } else {
                String needPassPrefix = "필요 통과 수: ";
                String needPass = "";
                if (snapshot.criteria.minimumPass != null) {
                    needPass += snapshot.criteria.minimumPass + " ~";
                }
                if (snapshot.criteria.maximumPass != null) {
                    if (needPass.isEmpty()) {
                        needPass = "~";
                    }
                    needPass += " " + snapshot.criteria.maximumPass;
                }
                if (!needPass.isEmpty()){
                    recursiveNode.add(new DefaultMutableTreeNode(needPassPrefix + needPass));
                }
            }

            for (RecursiveSnapshot subSnapshot : snapshot.subSnapshots) {
                addNode(recursiveNode, subSnapshot);
            }
        }

        parent.add(recursiveNode);
    }

    private void addNode(DefaultMutableTreeNode parent, LectureSnapshot snapshot) {
        if (snapshot.criteria.minimumGrade != null) {
            parent.add(new DefaultMutableTreeNode("인정 성적: " + snapshot.criteria.minimumGrade + " 이상"));
        }
        String validPeriodPrefix = "유효 이수 기간:";
        String validPeriod = "";
        Semester minSemester = snapshot.criteria.getMinimumSemester();
        Semester maxSemester = snapshot.criteria.getMaximumSemester();
        if (minSemester != null) {
            validPeriod += minSemester;
        }
        if (!validPeriod.isEmpty()) {
            validPeriod += " ~";
        }
        if (maxSemester != null) {
            validPeriod += maxSemester;
        }
        if (!validPeriod.isEmpty()) {
            parent.add(new DefaultMutableTreeNode(validPeriodPrefix + validPeriod));
        }
        if (snapshot.duplicatedDegrees.length > 0) {
            DefaultMutableTreeNode duplicated = new DefaultMutableTreeNode("사용 학위: " + snapshot.duplicatedDegrees.length + "개");
            parent.add(duplicated);

            for (String duplicatedDegree : snapshot.duplicatedDegrees) {
                duplicated.add(new DefaultMutableTreeNode(duplicatedDegree));
            }
        }
    }
}
