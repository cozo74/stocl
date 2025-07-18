package nju.ics.grammar.oclcore.elements;

public class OCLElement {

    protected String RAExpr="null";

    public OCLElement() {
    }

    public OCLElement(String RAExpr) {
        this.RAExpr = RAExpr;
    }

    public String getRAExpr() {
        return this.RAExpr;
    };
}
