package nju.ics.grammar.translator.elements;

import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;

import nju.ics.grammar.stocl.PrimitiveType;




public class ArithExpr extends OCLElement{


    boolean isRelation, isLiteral, isSubQueryOrRexNode;

    Literal literal; 

    RexNode scalarOrRexNode; 


    OCLBag refSet;


    public ArithExpr(OCLBag refSet) {
        super(refSet.getRelNode());
        this.isRelation = true;
        this.refSet = refSet;
    }


    public ArithExpr(Literal literal ) {
        super(null);
        this.isRelation = false;
        this.isLiteral = true;
        this.isSubQueryOrRexNode = false;
        this.literal = literal;
        this.refSet = null;
    }


    public ArithExpr(RexNode scalarOrRexNode ) {
        super(null);
        this.isRelation = false;
        this.isLiteral = false;
        this.isSubQueryOrRexNode = true;
        this.scalarOrRexNode = scalarOrRexNode;
    }




    public boolean isRelation() {
        return isRelation;
    }


    public boolean isLiteral() {
        return isLiteral;
    }

    public boolean isSubQueryOrRexNode() {
        return isSubQueryOrRexNode;
    }



    public RexLiteral getLiteral() {
        return literal.getValue();
    }

    public String getLiteralString() {
        return literal.getValueString();
    }


    public OCLBag getRefSet(){
        return refSet;
    }


    public PrimitiveType getLiteralType(){

        
        String literalType =  literal.getTypeString();

        PrimitiveType type;

        switch (literalType) {
            case "DECIMAL":
                type = PrimitiveType.INTEGER;
                break;
            case "DOUBLE":
                type = PrimitiveType.REAL;
                break;
            default:
                throw new RuntimeException();
        }

        return type;

    }





    public RexNode getScalarOrRexNode() {

        if (this.isRelation==true) throw new RuntimeException("must be scalar");
        if (this.isLiteral==true) throw new RuntimeException("must be scalar");

        return this.scalarOrRexNode;
    }


    public RexNode getRexNode(){

        if (this.isRelation==true) throw new RuntimeException("must not be relation");

        if(isLiteral) {
            return this.literal.getValue();
        } else if(isSubQueryOrRexNode) {
            return this.scalarOrRexNode;
        } else {
            throw new RuntimeException();
        }

    }



    public String getRexNodeString(){

        if (this.isRelation==true) throw new RuntimeException("must not be relation");

        if(isLiteral) {
            return this.literal.getValueString();
        } else if(isSubQueryOrRexNode) {
            return this.scalarOrRexNode.toString();
        } else {
            throw new RuntimeException();
        }


    }


}
