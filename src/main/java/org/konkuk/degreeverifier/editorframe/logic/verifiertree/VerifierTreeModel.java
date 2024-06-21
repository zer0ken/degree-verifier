package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.mainframe.logic.informationtree.nodes.InsufficientRootNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class VerifierTreeModel extends DefaultTreeModel {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final DefaultMutableTreeNode root;
    private final JTree tree;

    public VerifierTreeModel(JTree tree) {
        super(new DefaultMutableTreeNode("/"));
        root = (DefaultMutableTreeNode) getRoot();
        this.tree = tree;

        if (editorModel.getSelectedVerifier() != null) {
            update(editorModel.getSelectedVerifier());
        }

        editorModel.observe(EditorModel.On.VERIFIER_SELECTED, selectedVerifier -> updateTree((DegreeVerifier) selectedVerifier));
    }

    private void updateTree(DegreeVerifier selectedDegree) {
        root.removeAllChildren();
        if (selectedDegree == null) {
            return;
        }

        DefaultMutableTreeNode degreeNode = new DefaultMutableTreeNode(selectedDegree);
        DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode(
                "필요 학점: " + selectedDegree.criteria.minimumCredit + " 학점 이상"
        );
        degreeNode.add(minimumCreditNode);

        if (!selectedDegree.insufficientDegrees.isEmpty()) {
            DefaultMutableTreeNode insufficientRootNode = new DefaultMutableTreeNode(new InsufficientRootNode());
            for (String insufficientDegree : selectedDegree.insufficientDegrees) {
                insufficientRootNode.add(new DefaultMutableTreeNode(insufficientDegree));
            }
            degreeNode.add(insufficientRootNode);
        }

        addNode(degreeNode, selectedDegree.recursiveSnapshot);
        root.add(degreeNode);
        reload();
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
        parent.add(new DefaultMutableTreeNode("다른 학위에서 사용: " + (snapshot.criteria.isNonExclusive() ? "허가" : "불허")));
        if (snapshot.duplicatedDegrees.length > 0) {
            DefaultMutableTreeNode duplicated = new DefaultMutableTreeNode("사용 학위: " + snapshot.duplicatedDegrees.length + "개");
            parent.add(duplicated);

            for (String duplicatedDegree : snapshot.duplicatedDegrees) {
                duplicated.add(new DefaultMutableTreeNode(duplicatedDegree));
            }
        }
    }
}
