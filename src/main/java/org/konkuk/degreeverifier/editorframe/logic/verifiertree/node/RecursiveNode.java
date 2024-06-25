package org.konkuk.degreeverifier.editorframe.logic.verifiertree.node;

import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableLectureCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;
import org.konkuk.degreeverifier.editorframe.logic.verifiertree.VerifierTreeModel;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.LinkedList;

public class RecursiveNode extends DefaultMutableTreeNode {
    private final EditableRecursiveCriteria recursive;

    private boolean isLectureNode;

    private final DefaultMutableTreeNode minimumGradeNode = new DefaultMutableTreeNode();
    private final DefaultMutableTreeNode validPeriodNode = new DefaultMutableTreeNode();

    private final DefaultMutableTreeNode validPassNode = new DefaultMutableTreeNode();

    private final LinkedList<RecursiveNode> subNodes = new LinkedList<>();

    public RecursiveNode(EditableRecursiveCriteria recursive) {
        super(recursive);
        this.recursive = recursive;

        isLectureNode = recursive.lectureCriteria != null;

        if (isLectureNode) {
            minimumGradeNode.setUserObject(recursive.lectureCriteria.getValidGradeString());
            add(minimumGradeNode);

            validPeriodNode.setUserObject(recursive.lectureCriteria.getValidPeriodString());
            add(validPeriodNode);
        } else {
            validPassNode.setUserObject(recursive.getValidPassString());
            add(validPassNode);

            for (RecursiveCriteria subcriteria : recursive.subcriteria) {
                RecursiveNode subNode = new RecursiveNode((EditableRecursiveCriteria) subcriteria);
                subNodes.add(subNode);
                add(subNode);
            }
        }
    }

    private void switchToRecursiveNode(VerifierTreeModel model) {
        model.removeNodeFromParent(minimumGradeNode);
        model.removeNodeFromParent(validPeriodNode);
        model.insertNodeInto(validPassNode, this, 0);
    }

    private void switchToLectureNode(VerifierTreeModel model) {
        model.removeNodeFromParent(validPassNode);

        model.insertNodeInto(minimumGradeNode, this, 0);
        model.insertNodeInto(validPeriodNode, this, 1);
    }

    private void setNodeMode(boolean isLectureNode, VerifierTreeModel model) {
        if (this.isLectureNode != isLectureNode) {
            if (isLectureNode) {
                switchToLectureNode(model);
            } else {
                switchToRecursiveNode(model);
            }
            this.isLectureNode = isLectureNode;
        }
    }

    public void update(VerifierTreeModel model) {
        setUserObject(recursive);
        model.nodeChanged(this);
        setNodeMode(recursive.lectureCriteria != null, model);
        if (isLectureNode) {
            EditableLectureCriteria lecture = recursive.getEditableLectureCriteria();

            minimumGradeNode.setUserObject(lecture.getValidGradeString());
            model.nodeChanged(minimumGradeNode);

            validPeriodNode.setUserObject(lecture.getValidPeriodString());
            model.nodeChanged(validPeriodNode);

            if (!subNodes.isEmpty()) {
                for (RecursiveNode subNode : subNodes) {
                    model.removeNodeFromParent(subNode);
                }
            }
            subNodes.clear();
        } else {
            validPassNode.setUserObject(recursive.getValidPassString());
            model.nodeChanged(validPassNode);

            if (subNodes.size() > recursive.subcriteria.length) {
                LinkedList<RecursiveNode> removed = new LinkedList<>();
                for (RecursiveNode subNode : subNodes) {
                    if (subNode.recursive.removed) {
                        model.removeNodeFromParent(subNode);
                        removed.add(subNode);
                    }
                }
                subNodes.removeAll(removed);
            } else if (subNodes.size() < recursive.subcriteria.length) {
                RecursiveNode subNode = new RecursiveNode(
                        (EditableRecursiveCriteria) recursive.subcriteria[recursive.subcriteria.length - 1]);
                subNodes.add(subNode);
                model.insertNodeInto(subNode, this, getChildCount());
            } else {
                for (RecursiveNode subNode : subNodes) {
                    subNode.update(model);
                    model.nodeChanged(subNode);
                }
            }
        }
    }
}
