package nju.ics.grammar.translator.elements;

public class Var extends OCLElement{
    String varName; // the name of the variable
    String ClassName; // the class name of the variable


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
