package org.konkuk.degreeverifier.editorframe.logic.verifiertree.node;

import org.konkuk.degreeverifier.business.Semester;
import org.konkuk.degreeverifier.business.verify.criteria.RecursiveCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableDegreeCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableLectureCriteria;
import org.konkuk.degreeverifier.business.verify.editable.EditableRecursiveCriteria;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.LinkedList;

public class RecursiveNode extends DefaultMutableTreeNode {
    private final EditableRecursiveCriteria recursive;

    public RecursiveNode(EditableRecursiveCriteria recursive) {
        super(recursive);
        this.recursive = recursive;

        if (recursive.subcriteria != null) {
            for (RecursiveCriteria subcriteria : recursive.subcriteria) {
                add(new RecursiveNode((EditableRecursiveCriteria) subcriteria));
            }
        }

        update();
    }

    public void update() {
        if (recursive.lectureCriteria != null) {
            removeAllChildren();
            EditableLectureCriteria criteria = recursive.getEditableLectureCriteria();
            if (criteria.minimumGrade != null) {
                add(new DefaultMutableTreeNode("인정 성적: " + criteria.minimumGrade + " 이상"));
            }
            String validPeriodPrefix = "유효 이수 기간: ";
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
                validPeriod += " " + maxSemester;
            }
            if (!validPeriod.isEmpty()) {
                add(new DefaultMutableTreeNode(validPeriodPrefix + validPeriod));
            }
        } else {
            LinkedList<MutableTreeNode> removedChildren = new LinkedList<>();
            for (Object child : children) {
                if(child instanceof RecursiveNode) {
                    ((RecursiveNode) child).update();
                } else {
                    removedChildren.add((MutableTreeNode) child);
                }
            }
            for (MutableTreeNode removedChild : removedChildren) {
                remove(removedChild);
            }
            if (recursive.needsAllPass()) {
                insert(new DefaultMutableTreeNode("통과 기준: 모든 하위 검사 통과"), 0);
            } else {
                String needPassPrefix = "필요 통과 수: ";
                String needPass = "";
                if (recursive.minimumPass != null) {
                    needPass += recursive.minimumPass + " ~";
                }
                if (recursive.maximumPass != null) {
                    if (needPass.isEmpty()) {
                        needPass = "~";
                    }
                    needPass += " " + recursive.maximumPass;
                }
                if (!needPass.isEmpty()) {
                    insert(new DefaultMutableTreeNode(needPassPrefix + needPass), 0);
                }
            }
        }
    }
}
