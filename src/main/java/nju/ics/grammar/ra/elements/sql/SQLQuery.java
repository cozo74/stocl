package nju.ics.grammar.ra.elements.sql;

import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;

public class SQLQuery {


    String sqlQuery = "";
    String alias = null;
    List<Pair<String, String>> colList;
    boolean needUseAlias = false;



    public SQLQuery() {
    }

    public SQLQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }


    public SQLQuery(String sqlQuery, List<Pair<String, String>> colList) {
        this.sqlQuery = sqlQuery;
        this.colList = colList;
    }


    public String getSQLQuery() {
        if (needUseAlias) {
            return "( " + sqlQuery + " )" + " AS " + alias;
        } else {
            return sqlQuery;
        }

    }


    public SQLQuery setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public void setColList(List<Pair<String, String>> colList) {
        this.colList = colList;
    }



    public List<Pair<String, String>> getColList() {
        return colList;
    }


    public List<String> getCols() {

        List<String> cols = new ArrayList<>();
        for (Pair<String, String> col : colList) {

            assert col.a != null : "Column name cannot be null";

            if (col.b != null) {
                cols.add(col.b);
            } else {
                cols.add(col.a);
            }
        }


        return cols;
    }



    public void setNeedUseAlias(boolean needUseAlias) {
        this.needUseAlias = needUseAlias;
    }


}
