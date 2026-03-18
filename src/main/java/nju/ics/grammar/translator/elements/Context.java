package nju.ics.grammar.translator.elements;

import java.util.List;

public class Context extends OCLElement{

    private final List<Inv> invariants;
    private final String contextName;

    public Context(String contextName, List<Inv> invariants) {
        super(invariants.get(0).getRelNode());
        this.contextName = contextName;
        this.invariants = invariants;
    }

    public String getContextName() {
        return contextName;
    }

    public List<Inv> getInvariants() {
        return invariants;
    }
}
