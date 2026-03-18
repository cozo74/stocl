package nju.ics.grammar.translator.elements;

import org.apache.calcite.rel.RelNode;




public class Inv extends OCLElement{

    private final String invName;
    private final String invExprString;


    public Inv(String invName, String invExprString, RelNode relNode ) {
        super(relNode);
        this.invName = invName;
        this.invExprString = invExprString;
    }

    public String getInvName() {
        return invName;
    }


    public String getInvExprString() {
        return invExprString;
    }

}
