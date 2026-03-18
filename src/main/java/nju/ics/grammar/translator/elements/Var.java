package nju.ics.grammar.translator.elements;

public class Var extends OCLElement{
    String varName; 
    String ClassName; 


    public Var(String varName, String className) {
        super(null);
        this.varName = varName;
        this.ClassName = className;
    }

    public String getVarName() {
        return varName;
    }

    public String getClassName() {
        return ClassName;
    }


}
