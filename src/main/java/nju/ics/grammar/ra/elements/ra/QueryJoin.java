package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;

public class QueryJoin extends Query{

    Query leftQuery;
    Query rightQuery;
    boolean hasCondition;
    String condition;


    public QueryJoin(Query leftQuery, Query rightQuery) {
        super();
        this.leftQuery = leftQuery;
        this.rightQuery = rightQuery;
        this.hasCondition = false;
        this.condition = null;


        this.attrList = new ArrayList<>(leftQuery.getAttrList());

        for (int i = 0; i < rightQuery.getAttrList().size(); i++) {
            if (!this.attrList.contains(rightQuery.getAttrList().get(i))) {
                this.attrList.add(rightQuery.getAttrList().get(i));
            }
        }


    }


    public QueryJoin(Query leftQuery, Query rightQuery, String condition) {
        super();
        this.leftQuery = leftQuery;
        this.rightQuery = rightQuery;
        this.hasCondition = true;
        this.condition = condition;


        this.attrList = new ArrayList<>(leftQuery.getAttrList());

        for (int i = 0; i < rightQuery.getAttrList().size(); i++) {
            if (!this.attrList.contains(rightQuery.getAttrList().get(i))) {
                this.attrList.add(rightQuery.getAttrList().get(i));
            }
        }
    }

    public Query getLeftQuery() {
        return leftQuery;
    }

    public Query getRightQuery() {
        return rightQuery;
    }

    public boolean hasCondition() {
        return hasCondition;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String getRAExpr() {
        if (hasCondition) {
            return leftQuery.getRAExpr() + " Join " + condition + " " + rightQuery.getRAExpr();
        } else {
            return leftQuery.getRAExpr() + " Join " + rightQuery.getRAExpr();
        }
    }


}
