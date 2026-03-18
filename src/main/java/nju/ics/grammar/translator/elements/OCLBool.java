package nju.ics.grammar.translator.elements;

import org.apache.calcite.rel.RelNode;

public class OCLBool extends OCLElement{

    // relNode 表示满足约束的对象

    public OCLBool(RelNode relNode) {
        super(relNode);
    }



}
