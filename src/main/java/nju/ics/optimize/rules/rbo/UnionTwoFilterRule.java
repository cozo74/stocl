package nju.ics.optimize.rules.rbo;

import java.util.List;

import org.apache.calcite.plan.RelOptRuleCall;

import org.apache.calcite.plan.RelRule;
import org.apache.calcite.plan.hep.HepRelVertex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.logical.LogicalUnion;
import org.apache.calcite.rel.rules.SubstitutionRule;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;



@Value.Enclosing
public class UnionTwoFilterRule extends RelRule<UnionTwoFilterRule.Config> implements SubstitutionRule {
    


    protected UnionTwoFilterRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public UnionTwoFilterRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {

        LogicalUnion topUnion = call.rel(0);
        LogicalProject leftProj = call.rel(1);
        LogicalFilter leftFilter = call.rel(2);
        TableScan leftScan = call.rel(3);       

        LogicalFilter rightFilter = call.rel(5);



        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(leftScan)
                .filter(builder.or(leftFilter.getCondition(), rightFilter.getCondition()))
                .project(builder.field(leftProj.getRowType().getFieldNames().get(0)))
                .build();


        replacement = convert(call.getPlanner(), replacement, topUnion.getConvention());

        call.transformTo(replacement);

    }




    public static boolean isSameSourceAndSameColumn(LogicalUnion union) {


        RelNode left  = ((HepRelVertex) union.getInput(0)).getCurrentRel();
        RelNode right = ((HepRelVertex) union.getInput(1)).getCurrentRel();


        if (!(left instanceof LogicalProject lp)) return false;
        if (!(right instanceof LogicalProject rp)) return false;

        RelNode leftFilter = ((HepRelVertex) lp.getInput()).getCurrentRel();
        RelNode rightFilter = ((HepRelVertex) rp.getInput()).getCurrentRel();

        if (!(leftFilter instanceof LogicalFilter lf)) return false;
        if (!(rightFilter instanceof LogicalFilter rf)) return false;

        RelNode leftScan = ((HepRelVertex) lf.getInput()).getCurrentRel();
        RelNode rightScan = ((HepRelVertex) rf.getInput()).getCurrentRel();

        if (!(leftScan instanceof LogicalTableScan lscan)) return false;
        if (!(rightScan instanceof LogicalTableScan rscan)) return false;


        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;

        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);


        if(left.getRowType().getFieldCount() != 1) return false;
        if(right.getRowType().getFieldCount() != 1) return false;

        if (!left.getRowType().getFieldNames().get(0).equals(idName)) return false;
        if (!right.getRowType().getFieldNames().get(0).equals(idName)) return false;


        return true;


    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableUnionTwoFilterRule.Config.builder().build()
            .withOperandSupplier(b0 -> b0.operand(LogicalUnion.class)
                                        .predicate(UnionTwoFilterRule::isSameSourceAndSameColumn)
                                        .inputs(
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs())),
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs()))
                                        )
                                );


        @Override default UnionTwoFilterRule toRule() {
            return new UnionTwoFilterRule(this);
        }
    }





}
