package nju.ics.grammar.oclcore.elements;

import java.util.List;


public class OCLSet extends OCLElement{

    final boolean isEntirety;

    final OCLSetType setType; 
    final String className; 
    final OCLValueType valueType; 

    final List<String> attrList; 
    final List<String> assoList;

    final String universalSet;

    final String varName;



    public OCLSet(OCLSetType setType, String className, OCLValueType valueType, List<String> attrList, List<String> assoList, String RAExpr, String universalSet, boolean isEntirety) {
        super(RAExpr);
        this.setType = setType;
        this.className = className;
        this.valueType = valueType;
        this.attrList = attrList;
        this.assoList = assoList;
        this.universalSet = universalSet;
        this.isEntirety = isEntirety;
        this.varName = null;
    }

    public OCLSet(OCLSetType setType, String className, OCLValueType valueType, List<String> attrList, List<String> assoList, String RAExpr, String universalSet, boolean isEntirety, String varName) {
        super(RAExpr);
        this.setType = setType;
        this.className = className;
        this.valueType = valueType;
        this.attrList = attrList;
        this.assoList = assoList;
        this.universalSet = universalSet;
        this.isEntirety = isEntirety;
        this.varName = varName;
    }



    public OCLSetType getSetType() {
        return setType;
    }

    public String getClassName() {
        return className;
    }

    public String getObjectId() {
        String clazz = this.className.toLowerCase();
        return clazz + "_id";
    }


    public OCLValueType getValueType() {
        return valueType;
    }

    public List<String> getAttrList() {
        return attrList;
    }

    public List<String> getAssoList() {
        return assoList;
    }


    public String getUniversalSet() {

        return this.universalSet;
    }

    public boolean getIsEntirety() {
        return this.isEntirety;
    }


    public String getVarName() { return this.varName; }


}
