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


/*
 *  LogicalMinus(all=[false])
        LogicalProject(car_id=[$0])
            LogicalTableScan(table=[[shenzhen, Car]])
        LogicalProject(car_id=[$0])
            LogicalFilter(condition=[<($1, $2)])
                LogicalTableScan(table=[[shenzhen, Car]])
    =>
    LogicalProject(car_id=[$0])
        LogicalFilter(condition=[NOT(<($1, $2))])
            LogicalTableScan(table=[[shenzhen, Car]])
 * 
 * 
 */
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


        // 索引对应（按 operandSupplier 的顺序）：
        // 0=顶层 Minus，1=Project，2=左 Scan，3=右 Project，4=右 Filter，5=右 Any
        LogicalMinus topMinus = call.rel(0);

        TableScan leftScan = call.rel(2);

        LogicalFilter rightFilter = call.rel(4);



        // 构建替代子树：Project(not Select(Scan))
        RelBuilder builder = call.builder();
        RelNode replacement = builder.push(leftScan)
                .filter(builder.not(rightFilter.getCondition()))
                .project(builder.field(0))
                .build();


        // 保持与原顶层 Project 相同的 convention
        replacement = convert(call.getPlanner(), replacement, topMinus.getConvention());

        // 替换为 Project(Select(Scan))
        call.transformTo(replacement);

    }




    /*
     * 上述的Minus模式中，谓词条件为： 
     * 1. 两个Project都只有一列id列 
     * 2. 两个TableScan的table相同
     */
    public static boolean isSameTableMinus(Minus minus) {
        

        // left = Project(Scan), right = Project(Filter(Scan))
        RelNode left  = ((HepRelVertex) minus.getInput(0)).getCurrentRel();
        RelNode right = ((HepRelVertex) minus.getInput(1)).getCurrentRel();

        if (!(left  instanceof LogicalProject lp)) return false;
        if (!(right instanceof LogicalProject rp)) return false;

        // 两个 Project 都只有一列
        if (lp.getRowType().getFieldCount() != 1) return false;
        if (rp.getRowType().getFieldCount() != 1) return false;


        RelNode leftScan  = ((HepRelVertex) lp.getInput()).getCurrentRel();
        if (!(leftScan instanceof LogicalTableScan lscan)) return false;

        RelNode rightFilter = ((HepRelVertex) rp.getInput()).getCurrentRel();
        if (!(rightFilter instanceof LogicalFilter rf)) return false;

        RelNode rightScan = ((HepRelVertex) rf.getInput()).getCurrentRel();
        if (!(rightScan instanceof LogicalTableScan rscan)) return false;


        // 1) 获取 TableScan 的表名
        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = ((LogicalTableScan) rscan).getTable().getQualifiedName();

        if (!lq.equals(rq)) return false;

        // 表名（最后一段）小写
        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);

        // 两个 Project 的字段名均为 tableLower+"_id"
        if (!(lp.getRowType().getFieldNames().get(0).equals(idName))) return false;
        if (!(rp.getRowType().getFieldNames().get(0).equals(idName))) return false;


        return true;

    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    /** Rule pattern.*/
    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableUniversalSetMinusTableFilterRule.Config.builder().build()
            .withOperandSupplier(b0 -> b0.operand(LogicalMinus.class)
                                        .predicate( UniversalSetMinusTableFilterRule::isSameTableMinus)
                                        .inputs(
                                            // 左输入
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            // 右输入
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalFilter.class).oneInput(b3 -> b3.operand(LogicalTableScan.class).noInputs()))
                                        )
                                );



        @Override default UniversalSetMinusTableFilterRule toRule() {
            return new UniversalSetMinusTableFilterRule(this);
        }
    }



}
