package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;
import java.util.List;

public class Group extends Query{

    List<String> groupAttrList;
    List<String> aggAttrList;
    List<String> aggFuncList;

    Query originalQuery;



    public Group(List<String> groupAttrList, List<String> aggFuncList, List<String> aggAttrList, Query originalQuery) {
        super();
        this.groupAttrList = groupAttrList;
        this.aggAttrList = aggAttrList;
        this.aggFuncList = aggFuncList;
        this.originalQuery = originalQuery;

        this.attrList = new ArrayList<>();

        for (String attr : groupAttrList) {
            this.attrList.add(attr);
        }

        for (int i = 0; i < aggAttrList.size(); i++) {
            this.attrList.add(aggFuncList.get(i) + "(" + aggAttrList.get(i) + ")");
        }

    }


    public List<String> getGroupAttrList() {
        return groupAttrList;
    }

    public List<String> getAggAttrList() {
        return aggAttrList;
    }

    public List<String> getAggFuncList() {
        return aggFuncList;
    }

    public Query getOriginalQuery() {
        return originalQuery;
    }

    @Override
    public String getRAExpr() {

        StringBuilder attrListStr = new StringBuilder();

        for (String s : attrList) {
            attrListStr.append(s).append(",");
        }
        attrListStr.deleteCharAt(attrListStr.length() - 1);


        return "Group { " + attrListStr + " } { " + originalQuery.getRAExpr() + " } ";
    }
}
