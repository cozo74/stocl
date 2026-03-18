package nju.ics.grammar.translator.elements;

import org.apache.calcite.rel.RelNode;

import nju.ics.grammar.stocl.PrimitiveType;

import java.util.ArrayList;
import java.util.List;

public class OCLBag extends OCLElement{


    PrimitiveType setElementType;
    String className; // the class name of the object set
    SetType setType;

    List<String> groupKeys = new ArrayList<>();


    public OCLBag(RelNode relNode, String className, PrimitiveType setElementType, SetType setType,
                  List<String> groupKeys) {
        super(relNode);
        this.className = className;
        this.setElementType = setElementType;
        this.setType = setType;
        this.groupKeys = groupKeys;

    }




    public boolean isValueSet() {
        return this.setElementType!=PrimitiveType.OBJECT;
    }

    public boolean isObjectSet() {
        return this.setElementType==PrimitiveType.OBJECT;
    }

    public PrimitiveType getSetElementType() {
        return this.setElementType;
    }



    public String getClassName() {
        return className;
    }


    public boolean isSingleSet() {
        return this.setType==SetType.SINGLE_SET;
    }


    public boolean isPowerSet() {
        return this.setType==SetType.POWER_SET;
    }

    public SetType getSetType() {
        return this.setType;
    }



    public List<String> getGroupKeys(){
        return groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys){
        this.groupKeys = groupKeys;
    }


    public void addGroupKey(String groupKey){
        groupKeys.add(groupKey);

    }


    public void removeGroupKey(String groupKey){
        groupKeys.remove(groupKey);

    }




    public enum SetType{
        SINGLE_SET,  // 单个集合
        POWER_SET    // 幂集，集合的集合
    }




}
