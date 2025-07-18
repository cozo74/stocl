package nju.ics.grammar.ra.elements.sql;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class SQLTable extends SQLQuery{


    public SQLTable() {
    }

    public SQLTable(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }


    public SQLTable(String alias, String sqlQuery, List<Pair<String, String>> colList) {
        this.alias = alias;
        this.sqlQuery = sqlQuery;
        this.colList = colList;
    }


    @Override
    public String getSQLQuery() {
        if (needUseAlias) {
            return  sqlQuery + " AS " + alias;
        } else {
            return sqlQuery;
        }
    }
}
