package org.konkuk.degreeverifier.editorframe.logic.verifiertree;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.models.EditorModel;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableLectureCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;
import org.konkuk.degreeverifier.editorframe.components.verifiertree.VerifierTree;

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

        if (editorModel.getSelectedDegree() != null) {
            updateTree(editorModel.getSelectedDegree());
        }

        editorModel.observe(EditorModel.On.CRITERIA_SELECTED, selectedDegree -> updateTree((EditableDegreeCriteria) selectedDegree));
    }

    private void updateTree(EditableDegreeCriteria selectedDegree) {
        root.removeAllChildren();
        if (selectedDegree == null) {
            return;
        }

        DefaultMutableTreeNode degreeNode = new DefaultMutableTreeNode(selectedDegree);
        DefaultMutableTreeNode minimumCreditNode = new DefaultMutableTreeNode(
                "필요 학점: " + selectedDegree.minimumCredit + " 학점 이상"
        );
        degreeNode.add(minimumCreditNode);

        addNode(degreeNode, selectedDegree.getEditableRecursiveCriteria());
        root.add(degreeNode);
        reload();
        tree.updateFoldingState();
    }

    private void addNode(DefaultMutableTreeNode parent, EditableRecursiveCriteria criteria) {
        DefaultMutableTreeNode recursiveNode = new DefaultMutableTreeNode(criteria);

        if (criteria.lectureCriteria != null) {
            addNode(recursiveNode, criteria.getEditableLectureCriteria());
        } else {
            if (criteria.needsAllPass()) {
                recursiveNode.add(new DefaultMutableTreeNode("통과 기준: 모든 하위 검사 통과"));
            } else {
                String needPassPrefix = "필요 통과 수: ";
                String needPass = "";
                if (criteria.minimumPass != null) {
                    needPass += criteria.minimumPass + " ~";
                }
                if (criteria.maximumPass != null) {
                    if (needPass.isEmpty()) {
                        needPass = "~";
                    }
                    needPass += " " + criteria.maximumPass;
                }
                if (!needPass.isEmpty()){
                    recursiveNode.add(new DefaultMutableTreeNode(needPassPrefix + needPass));
                }
            }

            for (RecursiveCriteria subcriteria_ : criteria.getSubcriteria()) {
                EditableRecursiveCriteria subcriteria = (EditableRecursiveCriteria) subcriteria_;
                addNode(recursiveNode, subcriteria);
            }
        }

        parent.add(recursiveNode);
    }

    private void addNode(DefaultMutableTreeNode parent, EditableLectureCriteria criteria) {
        if (criteria.minimumGrade != null) {
            parent.add(new DefaultMutableTreeNode("인정 성적: " + criteria.minimumGrade + " 이상"));
        }
        parent.add(new DefaultMutableTreeNode("다른 학위에서 사용: " + (criteria.isNonExclusive() ? "허가" : "불허")));
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
