package nju.ics.grammar.translator.elements;
import java.util.List;


public class Specification extends OCLElement{

    private final List<Context> contexts;
    private final String specName;



    public Specification(String specName, List<Context> contexts) {
        super(contexts.get(0).getRelNode());
        this.specName = specName;
        this.contexts = contexts;
    }


    public List<Context> getContexts() {
        return contexts;
    }

    public String getSpecName() {
        return specName;
    }





}
