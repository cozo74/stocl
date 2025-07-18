package nju.ics.grammar.ra.elements.sql;

import java.util.List;

import org.antlr.v4.runtime.misc.Pair;

public class SQLJoin  extends SQLQuery{
    

    String conditions=null;
    SQLQuery left;
    SQLQuery right;



    // for condition join
    public SQLJoin(String alias, String conditions, SQLQuery left, SQLQuery right, List<Pair<String, String>> colList) {
        this.alias = alias;
        this.conditions = conditions;
        this.left = left;
        this.right = right;
        this.colList = colList;
    }





    @Override
    public String getSQLQuery() {


        StringBuilder sb = new StringBuilder();


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

            sb.deleteCharAt(sb.length() - 1); // Remove the last comma

        } else {
            sb.append("*");
        }
        sb.append(" FROM ");


        if (conditions == null) {
            sb.append(left.getSQLQuery()).append(" NATURAL JOIN ").append(right.getSQLQuery());

        } else{
            sb.append(left.getSQLQuery()).append(" JOIN ").append(right.getSQLQuery());
            sb.append(" ON ").append(conditions);
        }




        if (needUseAlias) {
            sb.insert(0, "( ");
            sb.append(" ) AS ").append(alias);
        }





        return sb.toString();

    }






}
