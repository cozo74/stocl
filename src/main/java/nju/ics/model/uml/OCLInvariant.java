package nju.ics.model.uml;

import java.util.List;



public class OCLInvariant {

    final String name;
    final String expr;
    final boolean satisfied;
    final List<Integer> violatedObjects;


    public OCLInvariant(String name, String expr, boolean satisfied, List<Integer> violatedObjects) {
        this.name = name;
        this.expr = expr;
        this.satisfied = satisfied;
        this.violatedObjects = violatedObjects;
    }


    public String getName() {
        return name;
    }

    public String getExpr() {
        return expr;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public List<Integer> getViolatedObjects() {
        return violatedObjects;
    }



}


