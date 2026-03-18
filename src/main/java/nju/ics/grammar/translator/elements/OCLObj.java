package nju.ics.grammar.translator.elements;


public class OCLObj extends OCLElement{

    OCLBag refSet;

    public OCLObj(OCLBag refSet) {
        super(refSet.getRelNode());
        this.refSet = refSet;
    }


    public String getClassName() {
        return refSet.getClassName();
    }


    public OCLBag getRefSet(){
        return refSet;
    }


}
