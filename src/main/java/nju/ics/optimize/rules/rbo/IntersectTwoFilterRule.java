package nju.ics.optimize.rules.rbo;

import java.util.List;

import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelRule;
import org.apache.calcite.plan.hep.HepRelVertex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.apache.calcite.rel.logical.LogicalIntersect;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.rules.SubstitutionRule;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;

@Value.Enclosing
public class IntersectTwoFilterRule extends RelRule<IntersectTwoFilterRule.Config> implements SubstitutionRule {
    


    protected IntersectTwoFilterRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public IntersectTwoFilterRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {


        LogicalIntersect topIntersect = call.rel(0);
        LogicalProject leftProj = call.rel(1);
        LogicalFilter leftFilter = call.rel(2);
        TableScan leftScan = call.rel(3);       

        LogicalFilter rightFilter = call.rel(5);



        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(leftScan)
                .filter(builder.and(leftFilter.getCondition(), rightFilter.getCondition()))
                .project(builder.field(leftProj.getRowType().getFieldNames().get(0)))
                .build();


        replacement = convert(call.getPlanner(), replacement, topIntersect.getConvention());

        call.transformTo(replacement);

    }




    public static boolean isSameSourceAndSameColumn(LogicalIntersect intersect) {


        RelNode left  = ((HepRelVertex) intersect.getInput(0)).getCurrentRel();
        RelNode right = ((HepRelVertex) intersect.getInput(1)).getCurrentRel();


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
        Config DEFAULT = ImmutableIntersectTwoFilterRule.Config.builder().build()
            .withOperandSupplier(b0 -> b0.operand(LogicalIntersect.class)
                                        .predicate(IntersectTwoFilterRule::isSameSourceAndSameColumn)
                                        .inputs(
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs())),
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs()))
                                        )
                                );


        @Override default IntersectTwoFilterRule toRule() {
            return new IntersectTwoFilterRule(this);
        }
    }





}
