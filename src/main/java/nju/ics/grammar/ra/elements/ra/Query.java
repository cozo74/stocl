package nju.ics.grammar.ra.elements.ra;

import java.util.ArrayList;
import java.util.List;

public class Query {
    List<String> attrList;
    String RAExpr;


    public Query() {
    }

    public Query(String RAExpr) {
        this.RAExpr = RAExpr;
    }

    public Query(List<String> attrList) {
        this.attrList = new ArrayList<>(attrList) ;
    }


    public List<String> getAttrList() {
        return attrList;
    }



    public String getRAExpr() {
        return RAExpr;
    }


}
