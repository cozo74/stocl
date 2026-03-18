package nju.ics.grammar.translator.elements;



import java.util.List;

public class VarList extends OCLElement{


    List<Var> varNames;


    public VarList(List<Var> varNames) {
        super(null);
        this.varNames = varNames;
    }



    public List<String> getVarNames() {
        return varNames.stream()
                .map(var -> var.varName)
                .toList();
    }



}
