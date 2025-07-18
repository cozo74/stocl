package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;
import java.util.List;

public class RenameList extends Query{
    List<String> oldAttrList;
    List<String> newAttrList;


    public RenameList() {
        super();
        oldAttrList = new ArrayList<>();
        newAttrList = new ArrayList<>();
    }

    public RenameList(List<String> oldAttrList, List<String> newAttrList) {
        super();
        this.oldAttrList = oldAttrList;
        this.newAttrList = newAttrList;
    }

    public void addPair(String oldAttr, String newAttr) {
        oldAttrList.add(oldAttr);
        newAttrList.add(newAttr);
    }

    public List<String> getOldAttrList() {
        return oldAttrList;
    }

    public List<String> getNewAttrList() {
        return newAttrList;
    }

}
