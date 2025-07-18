package nju.ics.grammar.ra.elements.sql;

public class SQLEqualityExpr extends SQLQuery {

    final String leftExpr;
    final String rightExpr;
    final String operator;

    public SQLEqualityExpr(String leftExpr, String rightExpr, String operator) {
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
        this.operator = operator;
    }

    @Override
    public String getSQLQuery() {
        return leftExpr + operator + rightExpr;
    }



    public String getSQLQuery(String leftAlias, String rightAlias) {
        if (leftExpr.equals(rightExpr)){
            return leftAlias + "." + leftExpr + operator + rightAlias + "." + rightExpr;
        } else {
            return getSQLQuery();
        }
    }


    public String getLeftExpr() {
        return leftExpr;
    }

    public String getRightExpr() {
        return rightExpr;
    }

    public String getOperator() {
        return operator;
    }




}
