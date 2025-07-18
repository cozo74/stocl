package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;

public class QueryOpWithoutJoin extends Query{

    Query leftQuery;
    Query rightQuery;
    String op;

    public QueryOpWithoutJoin(Query leftQuery, Query rightQuery, String op) {
        super();
        this.leftQuery = leftQuery;
        this.rightQuery = rightQuery;
        this.op = op;


        this.attrList = new ArrayList<>(leftQuery.getAttrList());
    }


    public Query getLeftQuery() {
        return leftQuery;
    }

    public Query getRightQuery() {
        return rightQuery;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String getRAExpr() {
        return "(" + leftQuery.getRAExpr() + " " + op + " " + rightQuery.getRAExpr() + ")";
    }

}
