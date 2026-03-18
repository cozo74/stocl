package nju.ics.optimize.rules.rbo;

import java.util.List;

import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelRule;
import org.apache.calcite.plan.hep.HepRelVertex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Minus;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.apache.calcite.rel.logical.LogicalMinus;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.rules.SubstitutionRule;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;


@Value.Enclosing
public class UniversalSetMinusTableFilterRule extends RelRule<UniversalSetMinusTableFilterRule.Config> implements SubstitutionRule  {
    


    protected UniversalSetMinusTableFilterRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public UniversalSetMinusTableFilterRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {


        LogicalMinus topMinus = call.rel(0);

        TableScan leftScan = call.rel(2);

        LogicalFilter rightFilter = call.rel(4);



        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(leftScan)
                .filter(builder.not(rightFilter.getCondition()))
                .project(builder.field(0))
                .build();


        replacement = convert(call.getPlanner(), replacement, topMinus.getConvention());

        call.transformTo(replacement);

    }




    public static boolean isSameTableMinus(Minus minus) {
        

        RelNode left  = ((HepRelVertex) minus.getInput(0)).getCurrentRel();
        RelNode right = ((HepRelVertex) minus.getInput(1)).getCurrentRel();

        if (!(left  instanceof LogicalProject lp)) return false;
        if (!(right instanceof LogicalProject rp)) return false;

        if (lp.getRowType().getFieldCount() != 1) return false;
        if (rp.getRowType().getFieldCount() != 1) return false;


        RelNode leftScan  = ((HepRelVertex) lp.getInput()).getCurrentRel();
        if (!(leftScan instanceof LogicalTableScan lscan)) return false;

        RelNode rightFilter = ((HepRelVertex) rp.getInput()).getCurrentRel();
        if (!(rightFilter instanceof LogicalFilter rf)) return false;

        RelNode rightScan = ((HepRelVertex) rf.getInput()).getCurrentRel();
        if (!(rightScan instanceof LogicalTableScan rscan)) return false;


        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = ((LogicalTableScan) rscan).getTable().getQualifiedName();

        if (!lq.equals(rq)) return false;

        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);

        if (!(lp.getRowType().getFieldNames().get(0).equals(idName))) return false;
        if (!(rp.getRowType().getFieldNames().get(0).equals(idName))) return false;


        return true;

    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableUniversalSetMinusTableFilterRule.Config.builder().build()
            .withOperandSupplier(b0 -> b0.operand(LogicalMinus.class)
                                        .predicate( UniversalSetMinusTableFilterRule::isSameTableMinus)
                                        .inputs(
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs()))
                                        )
                                );



        @Override default UniversalSetMinusTableFilterRule toRule() {
            return new UniversalSetMinusTableFilterRule(this);
        }
    }



}
