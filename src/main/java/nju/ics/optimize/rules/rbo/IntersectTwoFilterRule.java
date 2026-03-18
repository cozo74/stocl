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

/*
 * 
 *  LogicalIntersect(all=[false])
        LogicalProject(car_id=[$0])
            LogicalFilter(condition=[>=($2, 0)])
                LogicalTableScan(table=[[shenzhen0, Car]])
        LogicalProject(car_id=[$0])
            LogicalFilter(condition=[<=($2, 360)])
                LogicalTableScan(table=[[shenzhen0, Car]])
    =>
    LogicalProject(car_id=[$0])
        LogicalFilter(condition=[SEARCH($2, Sarg[[0..360]])])
            LogicalTableScan(table=[[shenzhen0, Car]])
 */

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

        // 索引对应（按 operandSupplier 的顺序）：
        // 0=顶层 Intersection，1=左 Project，2=左 Filter，3=左 Scan，4=右 Project，5=右 Filter，6=右 Scan
        LogicalIntersect topIntersect = call.rel(0);
        LogicalProject leftProj = call.rel(1);
        LogicalFilter leftFilter = call.rel(2);
        TableScan leftScan = call.rel(3);       

        LogicalFilter rightFilter = call.rel(5);



        RelBuilder builder = call.builder();
        // 构建替代子树：Project(Select cond1 and cond2 (Scan))
        RelNode replacement = builder.push(leftScan)
                .filter(builder.and(leftFilter.getCondition(), rightFilter.getCondition()))
                .project(builder.field(leftProj.getRowType().getFieldNames().get(0)))
                .build();


        // 保持与原顶层 Intersect 相同的 convention
        replacement = convert(call.getPlanner(), replacement, topIntersect.getConvention());

        // 替换为 Project(Select cond1 and cond2 (Scan))
        call.transformTo(replacement);

    }




    /*
     * 上述的Intersection模式中，谓词条件为： 
     * 1. 两个TableScan的table相同， 
     * 2. 两个Project都只有一列id列
     * 
     */
    public static boolean isSameSourceAndSameColumn(LogicalIntersect intersect) {


        // left = Project(Filter(Scan)), right = Project(Filter(Scan))
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


        // 1) 两个 TableScan 的表相同
        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;

        // 表名（最后一段）小写
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



    /** Rule pattern. */
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
