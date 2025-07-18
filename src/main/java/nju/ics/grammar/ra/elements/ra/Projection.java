package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;
import java.util.List;

public class Projection extends Query{

    List<String> projAttrList;
    Query originalQuery;

    public Projection(List<String> projAttrList, Query originalQuery) {
        super();
        this.projAttrList = projAttrList;
        this.originalQuery = originalQuery;

        this.attrList = new ArrayList<>(projAttrList) ;
    }


    public List<String> getProjAttrList() {
        return projAttrList;
    }

    public Query getOriginalQuery() {
        return originalQuery;
    }

    @Override
    public String getRAExpr() {
        StringBuilder attrListStr = new StringBuilder();

        for (String attr : projAttrList) {
            attrListStr.append(attr).append(",");
        }
        attrListStr.deleteCharAt(attrListStr.length() - 1);

        return "Projection { " + attrListStr + " } { " + originalQuery.getRAExpr() + " } ";
    }

}
