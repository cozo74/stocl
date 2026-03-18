package nju.ics.grammar.translator.elements;

import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;

public class OCLElement {

    private final RelNode relNode;



    public OCLElement(RelNode relNode) {
        this.relNode = relNode;
    }



    public RelNode getRelNode() {
        return relNode;
    }


    public String getRAString() {
        return RelOptUtil.toString(relNode);
    }





}
