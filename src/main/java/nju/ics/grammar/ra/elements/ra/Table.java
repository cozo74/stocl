package nju.ics.grammar.ra.elements.ra;

import java.util.List;

public class Table extends Query{
    String tableName;

    public Table(String tableName, List<String> attrList) {
        super();
        this.tableName = tableName;
        this.attrList = attrList;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String getRAExpr() {
        return tableName;
    }
}
