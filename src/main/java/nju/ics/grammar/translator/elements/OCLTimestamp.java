package nju.ics.grammar.translator.elements;

import org.apache.calcite.rex.RexLiteral;

import nju.ics.grammar.stocl.PrimitiveType;

public class OCLTimestamp extends OCLElement{


    boolean isRelation;
    Literal literal;

    OCLBag refSet;

    public OCLTimestamp(OCLBag refSet) {
        super(refSet.getRelNode());
        this.isRelation = true;
        this.refSet = refSet;
    }


    public OCLTimestamp(Literal literal ) {
        super(null);
        this.isRelation = false;
        this.literal = literal;
        this.refSet = null;
    }

    public boolean isRelation() {
        return isRelation;
    }

    public RexLiteral getLiteral() {
        return literal.getValue();
    }

    public String getLiteralString() {return literal.getValueString();}

    public OCLBag getRefSet(){
        return refSet;
    }


    public PrimitiveType getLiteralType(){


        String literalType =  literal.getTypeString();

        PrimitiveType type;

        switch (literalType) {
            case "TIMESTAMP":
                type = PrimitiveType.TIMESTAMP;
                break;
            default:
                throw new RuntimeException("Literal type is not TIMESTAMP");
        }

        return type;

    }





}
