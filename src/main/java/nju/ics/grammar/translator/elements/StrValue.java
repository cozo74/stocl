package nju.ics.grammar.translator.elements;


import org.apache.calcite.rex.RexLiteral;

import nju.ics.grammar.stocl.PrimitiveType;


public class StrValue extends OCLElement{

    boolean isRelation; // true: relation, false: literal
    Literal literal; // when isRelation is false, store the literal value
    
    OCLBag refSet;

    public StrValue(OCLBag refSet) {
        super(refSet.getRelNode());
        this.isRelation = true;
        this.refSet = refSet;
    }


    public StrValue(Literal literal ) {
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
            case "CHAR":
                type = PrimitiveType.STRING;
                break;
            default:
                throw new RuntimeException("Literal type is not CHAR");
        }

        return type;

    }




}
