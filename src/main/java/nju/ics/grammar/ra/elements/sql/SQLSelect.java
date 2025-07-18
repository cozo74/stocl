package nju.ics.grammar.ra.elements.sql;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class SQLSelect extends SQLQuery{




    SQLQuery subQuery;

    String conditions, groupBy;


    public SQLSelect(String alias, List<Pair<String, String>> colList, SQLQuery subQuery, String conditions) {

        assert subQuery != null : " subQuery must be provided";

        this.alias = alias;
        this.colList = colList;
        this.subQuery = subQuery;
        this.conditions = conditions;
    }


    public SQLSelect(String alias, List<Pair<String, String>> colList, SQLQuery subQuery, String conditions, String groupBy) {

        assert subQuery != null : " subQuery must be provided";

        this.alias = alias;
        this.colList = colList;
        this.subQuery = subQuery;
        this.conditions = conditions;
        this.groupBy = groupBy;
    }


    @Override
    public String getSQLQuery(){



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

            sb.deleteCharAt(sb.length() - 1); 

        } else {
            sb.append("*");
        }

        sb.append(" FROM ").append(subQuery.getSQLQuery());



        if (conditions != null && !conditions.isEmpty()) {
            sb.append(" WHERE ").append(conditions);
        }



        if (groupBy != null && !groupBy.isEmpty()) {
            sb.append(" GROUP BY ").append(groupBy);
        }



        if (needUseAlias) {
            sb.insert(0, "( ");
            sb.append(" ) AS ").append(alias);
        }


        return sb.toString();

    }



    public SQLQuery getSubQuery() {
        return subQuery;
    }


    public String getConditions() {
        return conditions;
    }



}
