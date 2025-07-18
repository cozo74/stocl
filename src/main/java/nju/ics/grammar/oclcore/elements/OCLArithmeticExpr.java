package nju.ics.grammar.oclcore.elements;

public class OCLArithmeticExpr extends OCLElement{

    final OCLArithmeticExprType exprType;

    final OCLSet oclSet;
    final String literal;

    public OCLArithmeticExpr(OCLArithmeticExprType exprType, OCLSet oclSet, String literal, String RAExpr) {
        super(RAExpr);
        this.exprType = exprType;
        this.oclSet = oclSet;
        this.literal = literal;
    }


    public OCLArithmeticExprType getExprType() {
        return exprType;
    }

    public OCLSet getOclSet() {
        return oclSet;
    }

    public String getLiteral() {
        return literal;
    }



}
