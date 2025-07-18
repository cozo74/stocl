package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;
import java.util.List;

public class Rename extends Query{
    List<String> oldAttrList;
    List<String> newAttrList;
    Query originalQuery;

    public Rename(List<String> oldAttrList, List<String> newAttrList, Query originalQuery) {
        super();
        this.oldAttrList = oldAttrList;
        this.newAttrList = newAttrList;
        this.originalQuery = originalQuery;


        this.attrList = new ArrayList<>(this.originalQuery.getAttrList());

        for (int i = 0; i < this.oldAttrList.size(); i++) {
            int index = this.attrList.indexOf(this.oldAttrList.get(i));
            this.attrList.set(index, this.newAttrList.get(i));
        }

    }


    public List<String> getOldAttrList() {
        return oldAttrList;
    }

    public List<String> getNewAttrList() {
        return newAttrList;
    }

    public Query getOriginalQuery() {
        return originalQuery;
    }

    @Override
    public String getRAExpr() {
        StringBuilder attrRenameStr = new StringBuilder();

        for (int i = 0; i < oldAttrList.size(); i++) {
            attrRenameStr.append(oldAttrList.get(i)).append("->").append(newAttrList.get(i)).append(",");
        }
        attrRenameStr.deleteCharAt(attrRenameStr.length() - 1);

        return "Rename { " + attrRenameStr + " } { " + originalQuery.getRAExpr() + " } ";
    }





}
