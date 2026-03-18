package nju.ics.optimize.rules.rbo;


import java.util.ArrayList;
import java.util.List;

import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelRule;
import org.apache.calcite.plan.hep.HepRelVertex;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Join;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.rel.logical.LogicalJoin;
import org.apache.calcite.rel.logical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.rel.rules.SubstitutionRule;

import org.apache.calcite.rex.RexCall;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RelBuilderFactory;
import org.apache.calcite.util.ImmutableBitSet;
import org.immutables.value.Value;

import nju.ics.model.uml.UMLClassDiagram;




/*
 *  LogicalProject(car_id=[$0])
        LogicalJoin(condition=[AND(=($0, $2), <($1, $3))], joinType=[inner])
            LogicalProject(car_id=[$0], speed=[$1])
                LogicalTableScan(table=[[shenzhen, Car]])
            LogicalProject(car_id=[$0], direction=[$2])
                LogicalTableScan(table=[[shenzhen, Car]])
    =>
    LogicalProject(car_id=[$0])
        LogicalFilter(condition=[<($1, $2)])
            LogicalTableScan(table=[[shenzhen, Car]])
 * 
 * 
 */
@Value.Enclosing
public class TwoAttributeComparisonSelfJoinRule  extends RelRule<TwoAttributeComparisonSelfJoinRule.Config> implements SubstitutionRule {



    protected TwoAttributeComparisonSelfJoinRule(Config config) {
        super(config);
    }
    
    @Deprecated
    public TwoAttributeComparisonSelfJoinRule(RelBuilderFactory relBuilderFactory) {
        this(Config.DEFAULT.withRelBuilderFactory(relBuilderFactory)
                .as(Config.class));
    }



    @Override public void onMatch(RelOptRuleCall call) {


        // 索引对应（按 operandSupplier 的顺序）：
        // 0=顶层 Project，1=Join，2=左 Project，3=左 Scan，4=右 Project，5=右 Scan
        LogicalProject topProject = call.rel(0);
        Join join = call.rel(1);
        LogicalProject leftProj = call.rel(2);
        // TableScan leftScan = call.rel(3);
        LogicalProject rightProj = call.rel(4);

        TableScan rightScan = call.rel(5);


        // 3) 取出 join 条件中的第二个谓词（第一个已知是主键等值）
        List<RexNode> conj = RelOptUtil.conjunctions(join.getCondition());

        RexNode cond = conj.get(1);

        assert cond instanceof RexCall;
        RexCall rexCall = (RexCall) cond;


        String leftAttr =  leftProj.getRowType().getFieldNames().get(1);
        String rightAttr =  rightProj.getRowType().getFieldNames().get(1);

        List<String> tableFieldNames = rightScan.getRowType().getFieldNames();
        RelNode replacement;
        RelBuilder builder = call.builder();
        
        // 若 Project 只是简单的列引用，没有表达式
        if (tableFieldNames.containsAll(leftProj.getRowType().getFieldNames())
            && tableFieldNames.containsAll(rightProj.getRowType().getFieldNames())
            ) {

            // 构建替代子树：Project(Select(Scan))
            replacement = builder.push(rightScan)
                    .filter(builder.call(rexCall.getOperator(),
                            builder.field(leftAttr),
                            builder.field(rightAttr)
                            ))
                    .project(builder.field(0))
                    .build();


        } else {
        // Project 不是简单的列引用，存在表达式
            builder.push(rightScan);
            List<RexNode> proj = new ArrayList<>();
            proj.add(builder.field(0));
            proj.add(leftProj.getProjects().get(1));
            proj.add(rightProj.getProjects().get(1));
    
            List<String> alias = new ArrayList<>();
            alias.add( rightScan.getRowType().getFieldNames().get(0) );
            alias.add( leftAttr );
            alias.add( rightAttr );
    
            replacement = builder.project(proj, alias)
                    .filter(builder.call(rexCall.getOperator(),
                            builder.field(leftAttr),
                            builder.field(rightAttr)
                            ))
                    .project(builder.field(0))
                    .build();
    
        }



        // 保持与原顶层 Project 相同的 convention
        replacement = convert(call.getPlanner(), replacement, topProject.getConvention());

        // 替换为 Project(Select(Scan))
        call.transformTo(replacement);

    }




    /*
     * 上述的join模式中，谓词条件为： 
     * 1. 两个TableScan的table相同， 
     * 2. join类型为inner 
     * 3. join的condition中第一个为一个等值式，等值式两侧字段为Table名字小写+"_id" 
     * 4. 等值式两侧字段Table名字小写+"_id"为主键
     * 
     */
    public static boolean isPrimaryKeySelfJoin(Join join) {
        
        // 2) join 类型为 INNER
        if (join.getJoinType() != JoinRelType.INNER) return false;

        // left = Project(Scan), right = Project(Scan)
        RelNode left  = ((HepRelVertex) join.getLeft()).getCurrentRel();
        RelNode right = ((HepRelVertex) join.getRight()).getCurrentRel();

        if (!(left  instanceof LogicalProject lp)) return false;
        if (!(right instanceof LogicalProject rp)) return false;

        if (lp.getRowType().getFieldCount() != 2) return false;
        if (rp.getRowType().getFieldCount() != 2) return false;


        RelNode leftScan  = ((HepRelVertex) lp.getInput()).getCurrentRel();
        RelNode rightScan = ((HepRelVertex) rp.getInput()).getCurrentRel();

        if (!(leftScan instanceof LogicalTableScan lscan)) return false;
        if (!(rightScan instanceof LogicalTableScan rscan)) return false;


        // 1) 两个 TableScan 的表相同
        List<String> lq = lscan.getTable().getQualifiedName();
        List<String> rq = rscan.getTable().getQualifiedName();
        if (!lq.equals(rq)) return false;



        // 表名（最后一段）小写
        String tableLower = lq.get(lq.size() - 1);
        String idName = UMLClassDiagram.getObjectIDColumn(tableLower);



        // 3) 条件第一个必须是主键_id等值，第二个为任意二元比较
        List<RexNode> conj = RelOptUtil.conjunctions(join.getCondition());
        if (conj.size() != 2) return false;
        RexNode cond = conj.get(0);
        if (!(cond instanceof RexCall call)) return false;
        if (!(call.getKind() == SqlKind.EQUALS)) return false;
        if (call.getOperands().size() != 2) return false;

        RexNode a = call.getOperands().get(0);
        RexNode b = call.getOperands().get(1);
        if (!(a instanceof RexInputRef) ||
            !(b instanceof RexInputRef)) return false;

        // 连接两侧分别来自左输入、右输入
        int leftCount = left.getRowType().getFieldCount();
        int aIdx = ((RexInputRef) a).getIndex();
        int bIdx = ((RexInputRef) b).getIndex();
        boolean aFromLeft = aIdx < leftCount;
        boolean bFromLeft = bIdx < leftCount;
        if (aFromLeft == bFromLeft) return false; // 不是跨输入的等值

        // 取出左右侧**相对于各自输入**的字段索引与字段名
        int lIdx = aFromLeft ? aIdx : bIdx;               // 相对于 left(Project) 的索引
        int rIdx = aFromLeft ? bIdx - leftCount : aIdx - leftCount; // 相对于 right(Scan) 的索引
        if (rIdx < 0) return false;

        String leftFieldName  = left.getRowType().getFieldNames().get(lIdx);
        String rightFieldName = right.getRowType().getFieldNames().get(rIdx);


        // 3) 等值两侧字段名都等于 tableLower+"_id"
        if (!idName.equals(leftFieldName) || !idName.equals(rightFieldName)) return false;

        // 4) 两侧字段都为主键（唯一列）
        RelMetadataQuery mq = join.getCluster().getMetadataQuery();

        // 注意：左侧是 Project。Calcite 的元数据通常能把唯一性从输入传递上来；
        // 若左侧 Project 非恒等，需要你在规则整体设计里事先确保/约束它是恒等或直接引用该列。
        ImmutableBitSet lKey = ImmutableBitSet.of(lIdx);
        ImmutableBitSet rKey = ImmutableBitSet.of(rIdx);

        boolean leftUnique  = mq.areColumnsUnique(left,  lKey);
        boolean rightUnique = mq.areColumnsUnique(right, rKey);

        return leftUnique && rightUnique;

    }



    @Override public boolean autoPruneOld() {
        return true;
    }



    /** Rule pattern.*/
    @Value.Immutable
    public interface Config extends RelRule.Config {
        Config DEFAULT = ImmutableTwoAttributeComparisonSelfJoinRule.Config.builder().build()
            .withOperandSupplier(b -> b.operand(LogicalProject.class).oneInput(
                                        b0 -> b0.operand(LogicalJoin.class)
                                        .predicate(TwoAttributeComparisonSelfJoinRule::isPrimaryKeySelfJoin)
                                        .inputs(
                                            // 左输入
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs()),
                                            // 右输入
                                            b1 -> b1.operand(LogicalProject.class)
                                                    .oneInput(b2 -> b2.operand(LogicalTableScan.class).noInputs())
                                        )
                                    )
                                );



        @Override default TwoAttributeComparisonSelfJoinRule toRule() {
            return new TwoAttributeComparisonSelfJoinRule(this);
        }
    }


}
