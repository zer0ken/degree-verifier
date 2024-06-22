package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.criteria.DegreeCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.LectureCriteria;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.snapshot.LectureSnapshot;
import org.konkuk.degreeverifier.business.verify.snapshot.RecursiveSnapshot;
import org.konkuk.degreeverifier.business.verify.verifier.DegreeVerifier;
import org.konkuk.degreeverifier.business.verify.verifier.RecursiveVerifier;
import org.konkuk.degreeverifier.editorframe.components.verifiertree.VerifierTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class VerifierTreeModel extends DefaultTreeModel {
    private final EditorModel editorModel = EditorModel.getInstance();

    private final DefaultMutableTreeNode root;
    private final VerifierTree tree;

    public VerifierTreeModel(VerifierTree tree) {
        super(new DefaultMutableTreeNode("/"));
        root = (DefaultMutableTreeNode) getRoot();
        this.tree = tree;

        if (editorModel.getSelectedCriteria() != null) {
            updateTree(editorModel.getSelectedCriteria());
        }

        editorModel.observe(EditorModel.On.CRITERIA_SELECTED, selectedVerifier -> updateTree((DegreeVerifier) selectedVerifier));
    }

    private void updateTree(DegreeCriteria selectedCriteria) {
        root.removeAllChildren();
        if (selectedCriteria == null) {
            return;
        }

        DefaultMutableTreeNode degreeNode = new DefaultMutableTreeNode(selectedCriteria);
        DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode(
                "필요 학점: " + selectedCriteria.minimumCredit + " 학점 이상"
        );
        degreeNode.add(minimumCreditNode);

        addNode(degreeNode, selectedCriteria.recursiveCriteria);
        root.add(degreeNode);
        reload();
        tree.updateFoldingState();
    }

    private void addNode(DefaultMutableTreeNode parent, RecursiveCriteria criteria) {
        DefaultMutableTreeNode recursiveNode = new DefaultMutableTreeNode(criteria);

        if (criteria.lectureCriteria != null) {
            addNode(recursiveNode, criteria.lectureCriteria);
        } else {
            if (criteria.needsAllPass()) {
                recursiveNode.add(new DefaultMutableTreeNode("통과 기준: 모든 하위 검사 통과"));
            } else if (criteria.minimumPass != null) {
                recursiveNode.add(new DefaultMutableTreeNode(
                        "통과 기준: 최소 " + criteria.minimumPass + "개의 하위 검사 통과"));
            }

            if (criteria.maximumPass != null) {
                recursiveNode.add(new DefaultMutableTreeNode(
                        "학점 제한: 최대 " + criteria.maximumPass + "개의 하위 검사만 사용"));
            }

            for (RecursiveCriteria subCriteria : criteria.getSubcriteria()) {
                addNode(recursiveNode, subCriteria);
            }
        }

        parent.add(recursiveNode);
    }

    private void addNode(DefaultMutableTreeNode parent, LectureCriteria criteria) {
        parent.add(new DefaultMutableTreeNode("과목명: " + criteria.lectureName));
        if (criteria.minimumGrade != null) {
            parent.add(new DefaultMutableTreeNode("인정 성적: " + criteria.minimumGrade + " 이상"));
        }
        String validPeriodPrefix = "유효 기간:";
        String validPeriodPostfix = " 교과목만 인정";
        String validPeriod = "";
        Semester minSemester = criteria.getMinimumSemester();
        Semester maxSemester = criteria.getMaximumSemester();
        if (minSemester != null) {
            validPeriod += minSemester.toString();
        }
        if (!validPeriod.isEmpty()) {
            validPeriod += " ~";
        }
        if (maxSemester != null) {
            validPeriod += maxSemester.toString();
        }
        if (!validPeriod.isEmpty()) {
            parent.add(new DefaultMutableTreeNode(validPeriodPrefix + validPeriod + validPeriodPostfix));
        }
    }
}
