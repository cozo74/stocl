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

/*
 * 
*     LogicalUnion(all=[false])
        LogicalProject(car_id=[$0])
          LogicalFilter(condition=[=($3, 0)])
            LogicalTableScan(table=[[shenzhen0, Car]])
        LogicalProject(car_id=[$0])
          LogicalFilter(condition=[=($3, 1)])
            LogicalTableScan(table=[[shenzhen0, Car]])
    =>
    LogicalProject(car_id=[$0])
      LogicalFilter(condition=[SEARCH($3, Sarg[(-∞..0), (0..1), (1..+∞)])])
        LogicalTableScan(table=[[shenzhen0, Car]])
 */

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

        // 索引对应（按 operandSupplier 的顺序）：
        // 0=顶层 Intersection，1=左 Project，2=左 Filter，3=左 Scan，4=右 Project，5=右 Filter，6=右 Scan
        LogicalUnion topUnion = call.rel(0);
        LogicalProject leftProj = call.rel(1);
        LogicalFilter leftFilter = call.rel(2);
        TableScan leftScan = call.rel(3);       

        LogicalFilter rightFilter = call.rel(5);



        RelBuilder builder = call.builder();
        // 构建替代子树：Project(Select cond1 or cond2 (Scan))
        RelNode replacement = builder.push(leftScan)
                .filter(builder.or(leftFilter.getCondition(), rightFilter.getCondition()))
                .project(builder.field(leftProj.getRowType().getFieldNames().get(0)))
                .build();


        // 保持与原顶层 Union 相同的 convention
        replacement = convert(call.getPlanner(), replacement, topUnion.getConvention());

        // 替换为 Project(Select cond1 or cond2 (Scan))
        call.transformTo(replacement);

    }




    /*
     * 上述的 Union 模式中，谓词条件为： 
     * 1. 两个TableScan的table相同， 
     * 2. 两个Project都只有一列id列
     * 
     */
    public static boolean isSameSourceAndSameColumn(LogicalUnion union) {


        // left = Project(Filter(Scan)), right = Project(Filter(Scan))
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
