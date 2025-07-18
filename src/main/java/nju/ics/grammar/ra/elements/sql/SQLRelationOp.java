package nju.ics.grammar.ra.elements.sql;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class SQLRelationOp extends SQLQuery{


    String op, conditions=null;
    SQLQuery left;
    SQLQuery right;


    boolean resetColList = false;


    public SQLRelationOp(String alias, String op, SQLQuery left, SQLQuery right, List<Pair<String, String>> colList, boolean resetColList) {
        this.alias = alias;
        this.op = op;
        this.left = left;
        this.right = right;
        this.colList = colList;
        this.resetColList = resetColList;
    }




    @Override
    public String getSQLQuery() {


        StringBuilder sb = new StringBuilder();

        if (resetColList) {
            sb.append("SELECT ");
            if (colList != null && !colList.isEmpty()) {
                for (Pair<String, String> col : colList) {

                    assert col.a != null : "Column name cannot be null";

                    if (col.b != null) {
                        sb.append(col.a).append(" AS ").append(col.b).append(",");
                    } else {
                        sb.append(col.a).append(",");
                    }
                }

                sb.deleteCharAt(sb.length() - 1); 

            } else {
                sb.append("*");
            }
            sb.append(" FROM ");
            sb.append("(").append("(").append(left.getSQLQuery()).append(") ").append(op).append(" (").append(right.getSQLQuery()).append(")").append(")");
            sb.append(" AS ").append(alias);

        } else {
            sb.append("(").append(left.getSQLQuery()).append(") ").append(op).append(" (").append(right.getSQLQuery()).append(")");
        }


        if (needUseAlias) {
            sb.insert(0, "( ");
            sb.append(" ) AS ").append(alias);
        }


        return sb.toString();

    }


    public String getOp() {
        return op;
    }


    public void setResetColList(boolean resetColList) {
        this.resetColList = resetColList;
    }

}
