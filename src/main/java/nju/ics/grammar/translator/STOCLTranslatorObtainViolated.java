package nju.ics.grammar.translator;

import java.util.HashSet;

import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;

import nju.ics.grammar.stocl.STOCLParser;
import nju.ics.grammar.translator.elements.Inv;
import nju.ics.grammar.translator.elements.OCLBool;
import nju.ics.grammar.translator.elements.OCLElement;
import nju.ics.grammar.translator.elements.OCLBag;
import nju.ics.model.uml.UMLClassDiagram;

public class STOCLTranslatorObtainViolated extends STOCLTranslator{


    public STOCLTranslatorObtainViolated(UMLClassDiagram umlClassDiagram) {
        super(umlClassDiagram);
    }



    @Override
    public OCLElement visitInv(STOCLParser.InvContext ctx) {


        String invName = ctx.ID() != null ? ctx.ID().getText() : null;
        OCLBool oclBool = (OCLBool) visit(ctx.oclBool());

        OCLBag universalSet = varEnv.resolve("self");


        if (!new HashSet<>(universalSet.getRelNode().getRowType().getFieldNames()).equals(new HashSet<>(oclBool.getRelNode().getRowType().getFieldNames()))) {
            throw new RuntimeException("The columns are not same.");
        }

        RelNode inv = builder.push(universalSet.getRelNode())
                .push(oclBool.getRelNode())
                .minus(false, 2)
                .build();


        return new Inv(invName, ctx.getText(), inv);


    }




}
