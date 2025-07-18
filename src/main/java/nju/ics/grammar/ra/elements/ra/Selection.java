package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;

public class Selection extends Query{

    String condition;
    Query originalQuery;

    public Selection(String condition, Query originalQuery) {
        super();
        this.condition = condition;
        this.originalQuery = originalQuery;

        this.attrList = new ArrayList<>(originalQuery.getAttrList()) ;
    }


    public String getCondition() {
        return condition;
    }

    public Query getOriginalQuery() {
        return originalQuery;
    }

    @Override
    public String getRAExpr() {
        return "Selection { " + condition + " } { " + originalQuery.getRAExpr() + " } ";
    }
}
