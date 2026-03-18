package nju.ics.grammar.translator.elements;

import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;

import nju.ics.grammar.stocl.PrimitiveType;


public class Geometry extends OCLElement {


    boolean isRelation, isLiteral, isRexNode; // true: relation (RelNode), false: literal or scalar (RexNode)

    Literal literal; // when isRelation is false and isLiteral is true, store the literal value

    RexNode rexNode; //when isRelation is false and isLiteral is false, store the RexSubQuery value


    OCLBag refSet;


    public Geometry(OCLBag refSet) {
        super(refSet.getRelNode());
        this.isRelation = true;
        this.isLiteral = false;
        this.isRexNode = false;
        this.refSet = refSet;
    }


    public Geometry(Literal literal ) {
        super(null);
        this.isRelation = false;
        this.isLiteral = true;
        this.isRexNode = false;
        this.literal = literal;
        this.refSet = null;
    }


    public Geometry(RexNode rexNode ) {
        super(null);
        this.isRelation = false;
        this.isLiteral = false;
        this.isRexNode = true;
        this.rexNode = rexNode;
        this.refSet = null;
    }




    public boolean isRelation() {
        return isRelation;
    }


    public boolean isLiteral() {
        return isLiteral;
    }


    public boolean isRexNode() {
        return isRexNode;
    }





    public OCLBag getRefSet(){
        return refSet;
    }


    public RexLiteral getLiteral() {
        if (!this.isLiteral) throw new RuntimeException("must be Literal");

        return literal.getValue();
    }

    public PrimitiveType getLiteralType(){

        return PrimitiveType.GEOMETRY;

    }

    public RexNode getRexNode(){

        if (this.isRelation) throw new RuntimeException("must not be relation");
        if (!this.isRexNode) throw new RuntimeException("must be RexNode");

        return this.rexNode;


    }


    public String getRexNodeString(){

        if (this.isRelation==true) throw new RuntimeException("must not be relation");
        if (this.isRexNode==false) throw new RuntimeException("must be RexNode");

        return this.rexNode.toString();



    }

    
    
}
